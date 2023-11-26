package com.weverses.modempro.hook.hooks.phone

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean

// Platforms Support:
// sm8450/sm8475/sm7475/sm8550
// Framework/Telephone Services Support:
// xm13 series Only
object BandCondition : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "com.android.phone.MiuiPhoneUtils",
            "isSuitDualBandCondition",
            true,
            "phone"
        )
    }
}