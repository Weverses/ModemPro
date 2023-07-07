package com.weverses.modempro.hook.hooks.phone

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import com.weverses.modempro.util.Utils.hookMethodOfField

// Platforms Support:
// sm8450/sm8475/sm7475/sm8550
// Framework/Telephone Services Support:
// xm13 series Only
object DualdataConcurrent : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "com.android.phone.dsda.MiuiDualDataController",
            "isConcurrentWhiteListApp",
            true,
            "phone"
        )
        hookMethodOfBoolean(
            "com.android.phone.dsda.MiuiDualDataController",
            "isRedundantWhiteListApp",
            false,
            "phone"
        )
        hookMethodOfField(
            "com.android.phone.dsda.MiuiDualDataController",
            "processForegroundInfoEvent",
            "mConcurrentWhiteListForground",
            "true",
            "phone"
        )
        hookMethodOfField(
            "com.android.phone.dsda.MiuiDualDataController",
            "processForegroundInfoEvent",
            "mRedundantWhiteListForground",
            "false",
            "phone"
        )
    }
}