package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.isMTK
import de.robv.android.xposed.XposedBridge

// Platforms Support:
// sm8450/sm8475/sm7475/sm8550
// Framework/Telephone Services Support:
// xm13 series Only
object OptimizationSupport : BaseHook() {
    override fun init() {
        try {
            loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isAirportOptimizationSupported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemX55Pro: Hook-phone isAirportOptimizationSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone isAirportOptimizationSupported failed!")
            XposedBridge.log(e)
        }
        try {
            loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isSubwayOptimizationSupported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemX55Pro: Hook-phone isSubwayOptimizationSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone isSubwayOptimizationSupported failed!")
            XposedBridge.log(e)
        }
        try {
            loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isHikingOptimizationSupported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemX55Pro: Hook-phone isHikingOptimizationSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone isHikingOptimizationSupported failed!")
            XposedBridge.log(e)
        }
        try {
            loadClass("com.android.phone.MiuiPhoneUtils").methodFinder().first {
                name == "isSpecialNetorkOptimizationSupported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemX55Pro: Hook-phone isSpecialNetorkOptimizationSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone isSpecialNetorkOptimizationSupported failed!")
            XposedBridge.log(e)
        }
        if (isMTK()) {
            try {
                loadClass("com.android.phone.MiuiPhoneUtils").methodFinder().first {
                    name == "isSpecialNetorkOptimizationSupportedMtk"
                }.createHook {
                    returnConstant(true)
                }
                XposedBridge.log("ModemX55Pro: Hook-phone isSpecialNetorkOptimizationSupportedMtk success!")
            } catch (e: Throwable) {
                XposedBridge.log("ModemX55Pro: Hook-phone isSpecialNetorkOptimizationSupportedMtk failed!")
                XposedBridge.log(e)
            }
        } else {
            try {
                loadClass("com.android.phone.MiuiPhoneUtils").methodFinder().first {
                    name == "isSpecialNetorkOptimizationSupportedQcom"
                }.createHook {
                    returnConstant(true)
                }
                XposedBridge.log("ModemX55Pro: Hook-phone isSpecialNetorkOptimizationSupportedQcom success!")
            } catch (e: Throwable) {
                XposedBridge.log("ModemX55Pro: Hook-phone isSpecialNetorkOptimizationSupportedQcom failed!")
                XposedBridge.log(e)
              }
           }
        }
}