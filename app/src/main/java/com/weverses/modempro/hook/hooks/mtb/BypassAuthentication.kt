package com.weverses.modempro.hook.hooks.mtb

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Check.getAndroidVersion
import com.weverses.modempro.util.Check.getMIUIVersion
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import com.weverses.modempro.util.Utils.hookMethodOfLong
import com.weverses.modempro.util.Utils.hookMethodOfString
import de.robv.android.xposed.XposedBridge

object BypassAuthentication : BaseHook() {
    override fun init() {
        hookMethodOfLong(
            "com.xiaomi.mtb.XiaoMiServerPermissionCheck",
            "updatePermissionClass",
            0L,
            "mtb"
        )
        // 在HyperOS上
        if (getMIUIVersion() > 14f || getAndroidVersion() > "13"){
            try {
                ClassUtils.loadClass("com.xiaomi.mtb.XiaoMiServerPermissionCheck").methodFinder().first {
                    name == "getClassErrorString"
                }.createHook{
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
}