package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object Test : BaseHook() {
    override fun init() {
        try {
            findMethod("android.util.MiuiMultiWindowUtils") {
                name == "multiFreeFormSupported"
            }.hookReturnConstant(true)
            XposedBridge.log("ModemX55Pro: HookTest multiFreeFormSupported success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: HookTest multiFreeFormSupported failed!")
            XposedBridge.log(e)
        }
    }
}

