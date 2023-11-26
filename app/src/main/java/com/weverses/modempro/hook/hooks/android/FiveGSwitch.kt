package com.weverses.modempro.hook.hooks.android

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean

object FiveGSwitch : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isHideFiveGAndNetwork",
            false,
            "framework"
        )
    }
}

