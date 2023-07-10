package com.weverses.modempro.hook.hooks.android

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean

object N1Band : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isN1Supported",
            true,
            "framework"
        )
    }
}

