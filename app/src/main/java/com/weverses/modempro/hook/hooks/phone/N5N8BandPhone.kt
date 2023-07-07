package com.weverses.modempro.hook.hooks.phone

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean

object N5N8BandPhone : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isN5Supported",
            true,
            "phone"
        )
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isN8Supported",
            true,
            "phone"
        )
    }
}

