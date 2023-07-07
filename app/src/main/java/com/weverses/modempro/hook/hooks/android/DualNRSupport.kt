package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge


object DualNRSupport : BaseHook() {
    override fun init() {
        try {
            loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isDualNrSupported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemPro: Hook isDualNrSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook isDualNrSupported failed!")
            XposedBridge.log(e)
        }
    }
}

