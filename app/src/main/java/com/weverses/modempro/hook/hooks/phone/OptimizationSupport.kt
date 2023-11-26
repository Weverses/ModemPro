package com.weverses.modempro.hook.hooks.phone

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Check.getMIUIVersion
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import com.weverses.modempro.util.Check.isMTK

// Platforms Support:
// sm8450/sm8475/sm7475/sm8550
// Framework/Telephone Services Support:
// xm13 series Only
object OptimizationSupport : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isAirportOptimizationSupported",
            true,
            "phone"
        )
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isSubwayOptimizationSupported",
            true,
            "phone"
        )
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isHikingOptimizationSupported",
            true,
            "phone"
        )

        if (getMIUIVersion() < 15f) {
            hookMethodOfBoolean(
                "com.android.phone.MiuiPhoneUtils",
                "isSpecialNetworkOptimizationSupported",
                true,
                "phone"
            )
            if (isMTK()) {
                hookMethodOfBoolean(
                    "com.android.phone.MiuiPhoneUtils",
                    "isSpecialNetworkOptimizationSupportedMtk",
                    true,
                    "phone"
                )
            } else {
                hookMethodOfBoolean(
                    "com.android.phone.MiuiPhoneUtils",
                    "isSpecialNetworkOptimizationSupportedQcom",
                    true,
                    "phone"
                )
            }
        }
    }
}