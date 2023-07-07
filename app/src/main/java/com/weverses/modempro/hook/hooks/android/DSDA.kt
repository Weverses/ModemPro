package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.isMTK
import de.robv.android.xposed.XposedBridge


object DSDA : BaseHook() {
    override fun init() {
        if(!isMTK()) {
            try {
                loadClass("miui.telephony.TelephonyManagerEx").methodFinder().first {
                    name == "isDsdaSupported"
                }.createHook{
                    returnConstant(true)
                }
                XposedBridge.log("ModemPro: Hook isDsdaSupported success!")
            } catch (e: Throwable) {
                XposedBridge.log("ModemPro: Hook isDsdaSupported failed!")
                XposedBridge.log(e)
            }
        }
    }
}

