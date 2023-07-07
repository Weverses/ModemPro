package com.weverses.modempro.hook.hooks.android

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import com.weverses.modempro.util.Check.isMTK

object DSDA : BaseHook() {
    override fun init() {
        if(!isMTK()) {
            hookMethodOfBoolean(
                "miui.telephony.TelephonyManagerEx",
                "isDsdaSupported",
                true,
                "phone"
            )
        }
    }
}

