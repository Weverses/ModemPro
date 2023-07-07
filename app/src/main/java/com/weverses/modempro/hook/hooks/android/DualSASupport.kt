package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object DualSASupport : BaseHook() {
    override fun init() {
        try {
            ClassUtils.loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isDualSaSupported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemPro: Hook isDualSaSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook isDualSaSupported failed!")
            XposedBridge.log(e)
        }
    }
}

