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
        hookMethodOfBoolean(
            "com.android.phone.MiuiPhoneUtils",
            "isSpecialNetorkOptimizationSupported",
            true,
            "phone"
        )
    }
}