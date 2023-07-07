package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.isMTK
import de.robv.android.xposed.XposedBridge


object DsdaSupport : BaseHook() {
    override fun init() {
        if(isMTK()) {
            try {
                loadClass("com.android.phone.MiuiPhoneUtils").methodFinder().first {
                    name == "isDSDASupported"
                }.createHook {
                    returnConstant(true)
                }
                XposedBridge.log("ModemPro: Hook-phone isDsdaSupported success!")
            } catch (e: Throwable) {
                XposedBridge.log("ModemPro: Hook-phone isDsdaSupported failed!")
                XposedBridge.log(e)
            }
        } else {
            try {
                loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                    name == "isDsdaSupported"
                }.createHook{
                    returnConstant(true)
                }
                XposedBridge.log("ModemPro: Hook-phone isDsdaSupported success!")
            } catch (e: Throwable) {
                XposedBridge.log("ModemPro: Hook-phone isDsdaSupported failed!")
                XposedBridge.log(e)
            }
            try {
                loadClass("com.android.phone.MiuiPhoneUtils").methodFinder().first {
                    name == "isDeviceDsdaSupportedByQcom"
                }.createHook {
                    returnConstant(true)
                }
                XposedBridge.log("ModemPro: Hook-phone isDsdaSupported success!")
            } catch (e: Throwable) {
                XposedBridge.log("ModemPro: Hook-phone isDsdaSupported failed!")
                XposedBridge.log(e)
            }
        }
    }
}

