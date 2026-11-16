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
import de.robv.android.xposed.XposedBridge

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

        // K40/alioth is hard-coded in HyperOS 3/4's DEVICE_MTB_NON_HOOK_LIST.
        // Without this, MtbAppBase.onCreate() returns before registering
        // OemHookAgent, so no permission hook can ever run.
        if (isAlioth()) {
            hookMethodOfBoolean(
                "com.xiaomi.mtb.MtbAppBase",
                "isMtbSupported",
                true,
                "mtb"
            )
            bypassAliothNonHookList()
        }
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

    private fun isAlioth(): Boolean {
        return getProp("ro.product.device").contains("alioth")
    }

    private fun bypassAliothNonHookList() {
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
                        val list = it.args.getOrNull(0)?.toString()
                        if (list != null && list.contains("alioth")) {
                            it.result = false
                        }
                    }
                }
            XposedBridge.log("ModemPro: Hook-mtb alioth non-hook-list bypass success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-mtb alioth non-hook-list bypass failed!")
            XposedBridge.log(e)
        }
    }
}