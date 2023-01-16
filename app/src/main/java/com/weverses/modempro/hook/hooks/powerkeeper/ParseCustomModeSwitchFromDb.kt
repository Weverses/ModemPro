package com.weverses.modempro.hook.powerkeeper

import com.github.kyuubiran.ezxhelper.utils.field
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.github.kyuubiran.ezxhelper.utils.putObject
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge


object ParseCustomModeSwitchFromDb : BaseHook() {
    override fun init() {
        try {
            findMethod("com.miui.powerkeeper.statemachine.DisplayFrameSetting") {
                name == "parseCustomModeSwitchFromDb"
            }.hookBefore {
                it.args[0] = "{\"fucSwitch\":\"true\"}"
                it.thisObject.putObject("mIsCustomFpsSwitch", "true")
                XposedBridge.log("RefreshRate: Hook powerkeeper-parseCustomModeSwitchFromDb success!")
            }
        } catch (e: Throwable) {
            XposedBridge.log("RefreshRate: Hook powerkeeper-parseCustomModeSwitchFromDb failed!")
        }
    }
}
