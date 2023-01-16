package com.weverses.modempro.hook.misettings

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge


object NewRefreshRateFragment : BaseHook() {
    override fun init() {
        try {
            findMethod("com.xiaomi.misettings.display.RefreshRate.NewRefreshRateFragment") {
                name == "b"
            }.hookBefore {
                it.args[0] = true
                XposedBridge.log("RefreshRate: Hook misettings-b success!")
            }
        } catch (e: Throwable) {
            XposedBridge.log("RefreshRate: Hook misettings-b failed!")
        }
    }
}
