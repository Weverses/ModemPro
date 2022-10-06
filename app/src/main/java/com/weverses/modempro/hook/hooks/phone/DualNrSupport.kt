package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object DualNrSupport : BaseHook() {
    override fun init() {
        try {
            findMethod("miui.telephony.TelephonyManagerEx") {
                name == "isDualNrSupported"
            }.hookReturnConstant(true)
            XposedBridge.log("ModemX55Pro: Hook phone-isDualNrSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook phone-isDualNrSupported failed!")
            XposedBridge.log(e)
        }
    }
}
