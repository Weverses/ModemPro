package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object ModemFeature : BaseHook() {
    override fun init() {
        try {
            findMethod("com.android.phone.FiveGManagerBase") {
                name == "getModemFeatureMode"
            }.hookAfter {
                it.args[0] = -1
                it.result = true
            }
                XposedBridge.log("ModemX55Pro: Hook phone-getModemFeatureMode success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook phone-getModemFeatureMode failed!")
            XposedBridge.log(e)
        }

        try {
            findMethod("com.android.phone.MiuiPhoneUtils") {
                name == "isModemFeatureSupported"
            }.hookAfter {
                it.args[0] = -1
            }
            XposedBridge.log("ModemX55Pro: Hook phone-isModemFeatureSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook phone-isModemFeatureSupported failed!")
            XposedBridge.log(e)
        }

        try {
            findMethod("com.android.phone.MiuiPhoneUtils") {
                name == "getModemFeatureFromDb"
            }.hookAfter {
                it.args[0] = -1
            }
            XposedBridge.log("ModemX55Pro: Hook phone-getModemFeatureFromDb success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook phone-getModemFeatureFromDb failed!")
            XposedBridge.log(e)
        }
    }
}
