package com.weverses.modempro.hook.hooks.phone

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean

object FiveGSwitch : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "miui.telephony.TelephonyManagerEx",
            "isHideFiveGAndNetwork",
            false,
            "phone"
        )
        hookMethodOfBoolean(
            "com.android.phone.utils.FiveGUtils",
            "isFiveGInvisableByDefault",
            false,
            "phone"
        )
        hookMethodOfBoolean(
            "com.android.phone.FiveGCloudController",
            "isFiveGSwitchInVisibleByCloud",
            false,
            "phone"
        )
        hookMethodOfBoolean(
            "com.android.phone.FiveGCloudController",
            "isFiveGSwitchVisibleByCloud",
            true,
            "phone"
        )
        hookMethodOfBoolean(
            "com.android.phone.utils.FiveGUtils",
            "isFiveGNetworkForceVisable",
            true,
            "phone"
        )
        hookMethodOfBoolean(
            "com.android.phone.FiveGCloudController",
            "isFiveGNetWorkInVisible",
            false,
            "phone"
        )
    }
}