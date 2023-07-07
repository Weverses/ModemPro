package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import de.robv.android.xposed.XposedBridge

object ViceSlotVolteButton : BaseHook() {
    override fun init() {
        try {
            Utils.exec("settings put global vice_slot_volte_data_enabled 1")
            XposedBridge.log("ModemPro: enable ViceSlotVolte success!")
            hookMethodOfBoolean(
                "com.android.phone.MiuiPhoneUtils",
                "shouldHideViceSlotVolteDataButton",
                false,
                "phone"
            )
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Set settings int failed!")
            XposedBridge.log(e)
        }
        hookMethodOfBoolean(
            "com.android.phone.MiuiPhoneUtils",
            "shouldHideSmartDualSimButton",
            false,
            "phone"
        )
    }
}