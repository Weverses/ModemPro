package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object DualNRSupport : BaseHook() {
    override fun init() {
        try {
            findMethod("miui.telephony.TelephonyManagerEx") {
                name == "isDualNrSupported"
            }.hookAfter {
                it.result = true
            }
            XposedBridge.log("ModemX55Pro: Hook isDualNrSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook isDualNrSupported failed!")
            XposedBridge.log(e)
        }
    }
}

