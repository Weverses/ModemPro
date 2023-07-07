package com.weverses.modempro.util

import com.weverses.modempro.util.Utils.checkClassIfExists
import com.weverses.modempro.util.Utils.getPlatform
import com.weverses.modempro.util.Utils.getProp

object Check {
    val DualdataDevices: Array<String> = arrayOf("fuxi","cas","nuwa","ishtar")

    fun isUnSupportedMIUIVersion(): Boolean {
        return (getProp("ro.miui.ui.version.code") == "V12") && (getProp("ro.miui.ui.version.code") == "V125" )
    }

    fun isMTK(): Boolean {
        return (getProp("Build.BRAND") == "MTK")
    }

    fun isKonaPlatform(): Boolean{
        // 感觉除了865平台之外其他平台确实用不到这个功能,那就隐藏起来吧:)
        return (getPlatform() == "kona")
    }

    fun islahainaPlatform(): Boolean{
        // 感觉除了888平台之外其他平台确实用不到这个功能,那就隐藏起来吧:)
        return (getPlatform() == "lahaina")
    }

    fun isSupportDualdataInServices(): Boolean {
        return checkClassIfExists(
            "miui.telephony.TelephonyManagerEx",
            "isDualDataSupported"
        )
    }
}