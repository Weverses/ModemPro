package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils
import de.robv.android.xposed.XposedBridge

object ViceSlotVolteButton : BaseHook() {
    override fun init() {
        try {
            Utils.exec("settings put global vice_slot_volte_data_enabled 1")
            XposedBridge.log("ModemPro: enable ViceSlotVolte success!")
            ClassUtils.loadClass("com.android.phone.MiuiPhoneUtils").methodFinder().first {
                name == "shouldHideViceSlotVolteDataButton"
            }.createHook{
                returnConstant(false)
            }
            XposedBridge.log("ModemPro: Hook phone-shouldHideViceSlotVolteDataButton success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook phone-shouldHideViceSlotVolteDataButton failed!")
            XposedBridge.log(e)
        }
        try {
            ClassUtils.loadClass("com.android.phone.MiuiPhoneUtils").methodFinder().first {
                name == "shouldHideSmartDualSimButton"
            }.createHook{
                returnConstant(false)
            }
            XposedBridge.log("ModemPro: Hook phone-shouldHideSmartDualSimButton success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook phone-shouldHideSmartDualSimButton failed!")
            XposedBridge.log(e)
        }
    }
}