package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

// Framework/Telephone Services Support:
// xm13 series Only
object HikingCity : BaseHook() {
    override fun init() {
        try {
            loadClass("com.android.phone.NetworkModeRecognition").methodFinder().first {
                name == "isCityWhiteList"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemX55Pro: Hook-phone isCityWhiteList success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone isCityWhiteList failed!")
            XposedBridge.log(e)
        }
    }
}