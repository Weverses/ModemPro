package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils
import de.robv.android.xposed.XposedBridge

object ViceSlotVolteButton : BaseHook() {
    override fun init() {
        try {
            Utils.exec("settings put global vice_slot_volte_data_enabled 1")
            XposedBridge.log("ModemX55Pro: enable ViceSlotVolte success!")
            findMethod("com.android.phone.MiuiPhoneUtils") {
                name == "shouldHideViceSlotVolteDataButton"
            }.hookReturnConstant(false)
            XposedBridge.log("ModemX55Pro: Hook phone-shouldHideViceSlotVolteDataButton success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook phone-shouldHideViceSlotVolteDataButton failed!")
            XposedBridge.log(e)
        }
        try {
            findMethod("com.android.phone.MiuiPhoneUtils") {
                name == "shouldHideSmartDualSimButton"
            }.hookReturnConstant(false)
            XposedBridge.log("ModemX55Pro: Hook phone-shouldHideSmartDualSimButton success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook phone-shouldHideSmartDualSimButton failed!")
            XposedBridge.log(e)
        }
    }
}