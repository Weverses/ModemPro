package com.weverses.modempro.hook.hooks.android

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean

object N5N8Band : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isN5Supported",
            true,
            "framework"
        )
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isN8Supported",
            true,
            "framework"
        )
    }
}

