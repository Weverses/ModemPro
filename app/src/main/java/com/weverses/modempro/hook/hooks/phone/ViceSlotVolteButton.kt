package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object ViceSlotVolteButton : BaseHook() {
    override fun init() {
        try {
            findMethod("com.android.phone.MiuiPhoneUtils") {
                name == "shouldHideViceSlotVolteDataButton"
            }.hookReturnConstant(false)
            XposedBridge.log("ModemX55Pro: Hook phone-shouldHideViceSlotVolteDataButton success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook phone-shouldHideViceSlotVolteDataButton failed!")
            XposedBridge.log(e)
        }
    }
}