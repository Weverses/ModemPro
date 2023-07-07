package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

// Platforms Support:
// sm8450/sm8475/sm7475/sm8550
// Framework/Telephone Services Support:
// xm13 series Only
object Optimization : BaseHook() {
    override fun init() {
        try {
            loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isAirportOptimizationSupported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemPro: Hook isAirportOptimizationSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook isAirportOptimizationSupported failed!")
            XposedBridge.log(e)
        }
        try {
            loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isSubwayOptimizationSupported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemPro: Hook isSubwayOptimizationSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook isSubwayOptimizationSupported failed!")
            XposedBridge.log(e)
        }
        try {
            loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isHikingOptimizationSupported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemPro: Hook isHikingOptimizationSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook isHikingOptimizationSupported failed!")
            XposedBridge.log(e)
        }
    }
}