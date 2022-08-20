package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object DualSASupport : BaseHook() {
    override fun init() {
        try {
            findMethod("miui.telephony.TelephonyManagerEx") {
                name == "isDualSaSupported"
            }.hookReturnConstant(true)
            XposedBridge.log("ModemX55Pro: Hook isDualSaSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook isDualSaSupported failed!")
            XposedBridge.log(e)
        }
    }
}

