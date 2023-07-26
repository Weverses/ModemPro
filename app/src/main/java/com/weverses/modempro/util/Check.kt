package com.weverses.modempro.util

import com.weverses.modempro.util.Utils.getDefReturn
import com.weverses.modempro.util.Utils.getMethodDefReturn
import com.weverses.modempro.util.Utils.getPlatform
import com.weverses.modempro.util.Utils.getProp
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

object Check {
    val DualdataDevices: Array<String> = arrayOf("fuxi","cas","nuwa","ishtar")

    fun isUnSupportedMIUIVersion(): Boolean {
        return (getProp("ro.miui.ui.version.code") == "V12") && (getProp("ro.miui.ui.version.code") == "V125" )
    }

    fun isMTK(): Boolean {
        return (getProp("Build.BRAND") == "MTK")
    }

    fun iskonaPlatform(): Boolean{
        // 感觉除了865平台之外其他平台确实用不到这个功能,那就隐藏起来吧:)
        return (getPlatform() == "kona")
    }

    fun islahainaPlatform(): Boolean{
        // 感觉除了888平台之外其他平台确实用不到这个功能,那就隐藏起来吧:)
        return (getPlatform() == "lahaina")
    }

    fun istaroPlatform(): Boolean{
        // 感觉除了8+平台之外其他平台确实用不到这个功能,那就隐藏起来吧:)
        return (getPlatform() == "taro")
    }

    val framework = "miui.telephony.TelephonyManagerEx"

    fun getDefBands() {
        val bandMethods = mapOf(
            "N5" to "isN5Supported",
            "N8" to "isN8Supported",
            "N28" to "isN28Supported"
        )
        val bands = StringBuilder()
        val supportedBands = mutableListOf<String>()
        for ((band, methodName) in bandMethods) {
            val isSupported = getDefReturn(framework, methodName)
            if (isSupported == "true") {
                supportedBands.add(band)
            }
        }
        bands.append(supportedBands.joinToString(","))
        val result = bands.toString()
        // 为了实现这个日志输出貌似只能这样写了emm
        XposedBridge.log("ModemPro: Def NR Bands Support: $result")

        val isN5Support = supportedBands.contains("N5")
        val isN8Support = supportedBands.contains("N8")
        val isN28Support = supportedBands.contains("N28")
    }

    fun getDefDuaNR(){
        val NR = getDefReturn(framework,"isDualNrSupported")
        val SA = getDefReturn(framework,"isDualSaSupported")
        val FiveG = StringBuilder()

        if (NR == "true") {
            FiveG.append("DualNR,")
        }
        if (SA == "true") {
            FiveG.append("DualSA,")
        }
        val result = FiveG.trimEnd(',')

        XposedBridge.log("ModemPro: Def 5G Support: $result")
    }

    // 检测是否支持Dualdata,perfers还有问题所以弃用了:)
    fun isSupportDualdataInServices(mDualdata: Class<*>?) {
        SharedPrefers.sharedPreferences.getBoolean("mDualData", false)
        if (mDualdata != null){
            SharedPrefers.setBoolean("mDualData", true)
        } else {
            SharedPrefers.setBoolean("mDualData", false)
        }
    }
}