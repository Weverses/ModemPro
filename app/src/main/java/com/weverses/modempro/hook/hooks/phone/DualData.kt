package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

// Platforms Support:
// sm8450/sm8475/sm7475/sm8550
// Framework/Telephone Services Support:
// xm13 series Only
object DualData : BaseHook() {
    override fun init() {
        try {
            loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isDualDataSupported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemX55Pro: Hook-phone isDualDataupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone isDualDataSupported failed!")
            XposedBridge.log(e)
        }
        try {
            ClassUtils.loadClass("com.android.phone.MiuiPhoneUtils").methodFinder().first {
                name == "shouldHideIntelligentDualSimButton"
            }.createHook{
                returnConstant(false)
            }
            XposedBridge.log("ModemX55Pro: Hook-phone shouldHideIntelligentDualSimButton success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone shouldHideIntelligentDualSimButton failed!")
            XposedBridge.log(e)
        }
    }
}