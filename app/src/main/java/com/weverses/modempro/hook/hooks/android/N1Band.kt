package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object N1Band : BaseHook() {
    override fun init() {
        try {
            findMethod("miui.telephony.TelephonyManagerEx") {
                name == "isN1Supported"
            }.hookReturnConstant(true)
            XposedBridge.log("ModemX55Pro: Hook isN1Supported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook isN1Supported failed!")
            XposedBridge.log(e)
        }
    }
}

