package com.weverses.modempro.util

import android.os.Build
import com.weverses.modempro.BuildConfig
import de.robv.android.xposed.XSharedPreferences
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader


object Utils {
    fun exec(command: String): String {
        var process: Process? = null
        var reader: BufferedReader? = null
        var `is`: InputStreamReader? = null
        var os: DataOutputStream? = null
        return try {
            process = Runtime.getRuntime().exec("su")
            `is` = InputStreamReader(process.inputStream)
            reader = BufferedReader(`is`)
            os = DataOutputStream(process.outputStream)
            os.writeBytes(
                command.trimIndent()
            )
            os.writeBytes("\nexit\n")
            os.flush()
            var read: Int
            val buffer = CharArray(4096)
            val output = StringBuilder()
            while (reader.read(buffer).also { read = it } > 0) {
                output.append(buffer, 0, read)
            }
            process.waitFor()
            output.toString()
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } finally {
            try {
                os?.close()
                `is`?.close()
                reader?.close()
                process?.destroy()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun exec(commands: Array<String>): String {
        val stringBuilder = java.lang.StringBuilder()
        for (command in commands) {
            stringBuilder.append(exec(command))
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        val prefs = XSharedPreferences(BuildConfig.APPLICATION_ID, "config")
        if (prefs.hasFileChanged()) {
            prefs.reload()
        }
        return prefs.getBoolean(key, defValue)
    }

    fun getProp(mKey: String): String = Class.forName("android.os.SystemProperties").getMethod("get", String::class.java).invoke(Class.forName("android.os.SystemProperties"), mKey).toString()

    fun getPlatform(): String {
        return getProp("ro.board.platform")
    }



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


    fun isSupportDevice(): Boolean {
        val DualdataSupportDevices = arrayOf("fuxi","cas")
        val device = Build.DEVICE
        for (device in DualdataSupportDevices) {
            if (device.equals(DualdataSupportDevices)) {
                return true
            }
        }
        return false
    }


}
