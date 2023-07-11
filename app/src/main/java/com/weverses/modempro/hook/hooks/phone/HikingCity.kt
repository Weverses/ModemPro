package com.weverses.modempro.hook.hooks.phone

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean

// Framework/Telephone Services Support:
// xm13 series Only
object HikingCity : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "com.android.phone.NetworkModeRecognition",
            "isCityWhiteList",
            true,
            "phone"
        )
    }
}