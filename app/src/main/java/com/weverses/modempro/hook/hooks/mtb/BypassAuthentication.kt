package com.weverses.modempro.hook.hooks.mtb

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Check.getAndroidVersion
import com.weverses.modempro.util.Check.getMIUIVersion
import com.weverses.modempro.util.Check.isOS2
import com.weverses.modempro.util.Utils.getProp
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import com.weverses.modempro.util.Utils.hookMethodOfLong
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

object BypassAuthentication : BaseHook() {
    override fun init() {
        if (isOS3OrNewer()) {
            // HyperOS 3/4 can use either the new MtbAppBase/MtbNetworkServerMgrCheck
            // flow or the old MtbApp/XiaoMiServerPermissionCheck flow depending on
            // the MTB build.  Detect the actual classes instead of assuming a
            // single layout.
            if (initNewMtb()) return
            if (initLegacyPermissionCheck()) return
            initMtbAppPermission()
            return
        }

        // 在HyperOS1上
        if (getMIUIVersion() > 14f || getAndroidVersion() > "13") {
            initLegacyPermissionCheck()
        }
        // 在HyperOS2上
        if (isOS2()) {
            initMtbAppPermission()
        }
        // 在Android 12上
        if (getAndroidVersion() < "13") {
            hookMethodOfBoolean(
                "com.xiaomi.mtb.MtbUtils",
                "isUserBuild",
                false,
                "mtb"
            )
        }
    }

    /**
     * HyperOS 3/4 MTB: permission state moved to MtbAppBase, and the old
     * XiaoMiServerPermissionCheck gate was replaced by MtbNetworkServerMgrCheck.
     */
    private fun initNewMtb(): Boolean {
        if (!classExists("com.xiaomi.mtb.MtbAppBase")) return false

        hookMethodOfLong(
            "com.xiaomi.mtb.MtbAppBase",
            "getMiServerPermissionClass",
            0L,
            "mtb"
        )

        // Make the local initial permission calculation return full access before
        // it contacts Xiaomi's authentication server.
        hookMethodOfBoolean(
            "com.xiaomi.modem.ModemUtils",
            "isFactoryBuild",
            true,
            "mtb"
        )

        if (classExists("com.xiaomi.mtb.MtbNetworkServerMgrCheck")) {
            // Defensive fallback if the getter above is inlined in a future build.
            hookMethodOfLong(
                "com.xiaomi.mtb.MtbNetworkServerMgrCheck",
                "getInitPermissionClass",
                0L,
                "mtb"
            )

            // class = 0 means "all bits".  If mClassExclude was initialised by an
            // earlier failed auth, checkClass(0, bit) can still reject some bits;
            // make class = 0 a true wildcard.
            try {
                ClassUtils.loadClass("com.xiaomi.mtb.MtbNetworkServerMgrCheck").methodFinder()
                    .filterByName("checkClass")
                    .first()
                    .createHook {
                        after {
                            if (it.args.getOrNull(0) == 0L) {
                                it.result = true
                            }
                        }
                    }
                XposedBridge.log("ModemPro: Hook-mtb MtbNetworkServerMgrCheck.checkClass success!")
            } catch (e: Throwable) {
                XposedBridge.log("ModemPro: Hook-mtb MtbNetworkServerMgrCheck.checkClass failed!")
                XposedBridge.log(e)
            }
        }

        // Keep the cached field at 0 even if the app writes it back.
        try {
            ClassUtils.loadClass("com.xiaomi.mtb.MtbAppBase").methodFinder()
                .filterByName("setMiServerPermissionClass")
                .first()
                .createHook {
                    before { it.args[0] = 0L }
                }
            XposedBridge.log("ModemPro: Hook-mtb MtbAppBase.setMiServerPermissionClass success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-mtb MtbAppBase.setMiServerPermissionClass failed!")
            XposedBridge.log(e)
        }

        // Some models are hard-coded in MTB's DEVICE_MTB_NON_HOOK_LIST or
        // DEVICE_MTB_NOT_SUPPORT_LIST.  Bypass both lists; otherwise
        // MtbAppBase.onCreate() returns before registering OemHookAgent.
        hookMethodOfBoolean(
            "com.xiaomi.mtb.MtbAppBase",
            "isMtbSupported",
            true,
            "mtb"
        )
        bypassMtbDeviceLists()
        return true
    }

    /**
     * Old MTB layout used on HyperOS 1 and some HyperOS 3 builds:
     * XiaoMiServerPermissionCheck + MtbApp.getMiServerPermissionClass().
     */
    private fun initLegacyPermissionCheck(): Boolean {
        if (!classExists("com.xiaomi.mtb.XiaoMiServerPermissionCheck")) return false

        try {
            ClassUtils.loadClass("com.xiaomi.mtb.XiaoMiServerPermissionCheck").methodFinder().first {
                name == "getClassErrorString"
            }.createHook {
                after {
                    it.result = null
                }
            }
            XposedBridge.log("ModemPro: Hook-mtb getClassErrorString success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-mtb getClassErrorString failed!")
            XposedBridge.log(e)
        }

        hookMethodOfBoolean(
            "com.xiaomi.modem.ModemUtils",
            "isUserBuild",
            false,
            "mtb"
        )
        hookMethodOfLong(
            "com.xiaomi.mtb.XiaoMiServerPermissionCheck",
            "updatePermissionClass",
            0L,
            "mtb"
        )
        hookMethodOfLong(
            "com.xiaomi.mtb.MtbApp",
            "getMiServerPermissionClass",
            0L,
            "mtb"
        )
        hookMethodOfBoolean(
            "com.xiaomi.mtb.MtbUtils",
            "isFactoryBuild",
            true,
            "mtb"
        )
        hookMethodOfBoolean(
            "com.xiaomi.mtb.MtbUtils",
            "isUserBuild",
            false,
            "mtb"
        )
        hookMethodOfLong(
            "com.xiaomi.mtb.XiaoMiServerPermissionCheck",
            "onGetInitPermissionClass",
            0L,
            "mtb"
        )
        return true
    }

    /**
     * HyperOS 2-style MTB: MtbApp holds the cached permission class.
     */
    private fun initMtbAppPermission() {
        hookMethodOfLong(
            "com.xiaomi.mtb.MtbApp",
            "getMiServerPermissionClass",
            0L,
            "mtb"
        )
        hookMethodOfBoolean(
            "com.xiaomi.modem.ModemUtils",
            "isFactoryBuild",
            true,
            "mtb"
        )
    }

    private fun isOS3OrNewer(): Boolean {
        // HyperOS uses values such as "OS3.0.x" / "OS4.0.29.0.XFRCNXM".
        val osName = getProp("ro.mi.os.version.name")
        Regex("""OS(\d+)""").find(osName)?.groupValues?.getOrNull(1)?.toIntOrNull()?.let {
            return it >= 3
        }
        // Fallback for port ROMs that removed ro.mi.os.version.name.
        val android = getAndroidVersion().substringBefore('.').toIntOrNull() ?: 0
        return android >= 16
    }

    private fun classExists(className: String): Boolean {
        return ClassUtils.loadClassOrNull(className) != null
    }

    /**
     * MTB has two device filters:
     *   DEVICE_MTB_NON_HOOK_LIST  -> checked via ModemUtils.stringContainStringList()
     *   DEVICE_MTB_NOT_SUPPORT_LIST -> checked via String.contains()
     *
     * Bypass both for the current device.  This is broader than the previous
     * alioth-only workaround: models such as K20 Pro can be blocked by either
     * list depending on the MTB build.
     */
    private fun bypassMtbDeviceLists() {
        try {
            ClassUtils.loadClass("com.xiaomi.modem.ModemUtils").methodFinder()
                .filterByName("stringContainStringList")
                .first {
                    parameterTypes.size == 2 &&
                        parameterTypes[0] == String::class.java &&
                        parameterTypes[1] == String::class.java
                }
                .createHook {
                    after {
                        val list = it.args.getOrNull(0)?.toString() ?: return@after
                        val device = it.args.getOrNull(1)?.toString() ?: return@after
                        if (isMtbDeviceList(list) && matchesDevice(list, device)) {
                            it.result = false
                        }
                    }
                }
            XposedBridge.log("ModemPro: Hook-mtb device-list helper bypass success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-mtb device-list helper bypass failed!")
            XposedBridge.log(e)
        }

        // MtbAppBase / ModemTestBoxMainBaseActivity use the list directly:
        //   DEVICE_MTB_NOT_SUPPORT_LIST.contains(deviceName)
        try {
            XposedHelpers.findAndHookMethod(
                String::class.java,
                "contains",
                CharSequence::class.java,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val list = param.thisObject as? String ?: return
                        val needle = param.args.getOrNull(0)?.toString() ?: return
                        if (needle.isEmpty()) return
                        if (isMtbDeviceList(list) && list.indexOf(needle) >= 0) {
                            param.result = false
                        }
                    }
                }
            )
            XposedBridge.log("ModemPro: Hook-mtb device-list contains bypass success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-mtb device-list contains bypass failed!")
            XposedBridge.log(e)
        }
    }

    private fun isMtbDeviceList(list: String): Boolean {
        return (list.indexOf("psyche") >= 0 && list.indexOf("alioth") >= 0) ||
            (list.indexOf("vayu") >= 0 && list.indexOf("yupei") >= 0)
    }

    private fun matchesDevice(list: String, device: String): Boolean {
        return list.split(",").any { item ->
            val codename = item.trim()
            codename.isNotEmpty() && device.indexOf(codename) >= 0
        }
    }
}