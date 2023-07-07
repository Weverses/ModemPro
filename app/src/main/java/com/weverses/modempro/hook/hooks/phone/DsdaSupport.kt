package com.weverses.modempro.hook.hooks.phone

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Check.isMTK
import com.weverses.modempro.util.Utils.hookMethodOfBoolean

object DsdaSupport : BaseHook() {
    override fun init() {
        if(isMTK()) {
            hookMethodOfBoolean(
                "com.android.phone.MiuiPhoneUtils",
                "isDSDASupported",
                true,
                "phone"
            )
        } else {
            hookMethodOfBoolean(
                "miui.telephony.TelephonyManagerEx",
                "isDsdaSupported",
                true,
                "phone"
            )

            hookMethodOfBoolean(
                "com.android.phone.MiuiPhoneUtils",
                "isDeviceDsdaSupportedByQcom",
                true,
                "phone"
            )
        }
    }
}

