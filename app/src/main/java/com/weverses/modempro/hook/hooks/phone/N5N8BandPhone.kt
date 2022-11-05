package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object N5N8BandPhone : BaseHook() {
    override fun init() {
        try {
            findMethod("miui.telephony.TelephonyManagerEx") {
                name == "isN5Supported"
            }.hookReturnConstant(true)
            XposedBridge.log("ModemX55Pro: Hook phone-isN5Supported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook phone-isN5Supported failed!")
            XposedBridge.log(e)
        }

        try {
            findMethod("miui.telephony.TelephonyManagerEx") {
                name == "isN8Supported"
            }.hookReturnConstant(true)
            XposedBridge.log("ModemX55Pro: Hook phone-isN8Supported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook phone-isN8Supported failed!")
            XposedBridge.log(e)
        }
    }
}
