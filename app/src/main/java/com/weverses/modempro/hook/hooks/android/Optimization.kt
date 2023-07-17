package com.weverses.modempro.hook.hooks.android

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean

// Platforms Support:
// sm8450/sm8475/sm7475/sm8550
// Framework/Telephone Services Support:
// xm13 series Only
object Optimization : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isAirportOptimizationSupported",
            true,
            "framework"
        )
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isSubwayOptimizationSupported",
            true,
            "framework"
        )
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isHikingOptimizationSupported",
            true,
            "framework"
        )
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isSpecialNetworkOptimizationSupported",
            true,
            "framework"
        )
    }
}