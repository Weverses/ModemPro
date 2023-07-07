package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object N28Band : BaseHook() {
    override fun init() {
        try {
            ClassUtils.loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                name == "isN28Supported"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemPro: Hook isN28Supported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook isN28Supported failed!")
            XposedBridge.log(e)
        }
    }
}

