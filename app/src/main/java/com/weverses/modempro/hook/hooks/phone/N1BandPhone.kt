package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object N1BandPhone : BaseHook() {
    override fun init() {
        try {
            ClassUtils.loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isN1Supported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemX55Pro: Hook-phone isN1Supported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone isN1Supported failed!")
            XposedBridge.log(e)
        }
    }
}
