package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object ModemFeature : BaseHook() {
    override fun init() {
        try {
            ClassUtils.loadClass("com.android.phone.FiveGManagerBase").methodFinder().first {
                name == "getModemFeatureMode"
            }.createHook{
                before { param ->
                    param.args[0] = -1
                }
                returnConstant(true)
            }
                XposedBridge.log("ModemPro: Hook phone-getModemFeatureMode success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook phone-getModemFeatureMode failed!")
            XposedBridge.log(e)
        }

        try {
            ClassUtils.loadClass("com.android.phone.MiuiPhoneUtils").methodFinder().first {
                name == "isModemFeatureSupported"
            }.createHook{
                before { param ->
                    param.args[0] = -1
                }
            }
            XposedBridge.log("ModemPro: Hook phone-isModemFeatureSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook phone-isModemFeatureSupported failed!")
            XposedBridge.log(e)
        }

        try {
            ClassUtils.loadClass("com.android.phone.MiuiPhoneUtils").methodFinder().first {
                name == "getModemFeatureFromDb"
            }.createHook{
                before { param ->
                    param.args[0] = -1
                }
            }

            XposedBridge.log("ModemPro: Hook phone-getModemFeatureFromDb success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook phone-getModemFeatureFromDb failed!")
            XposedBridge.log(e)
        }
    }
}
