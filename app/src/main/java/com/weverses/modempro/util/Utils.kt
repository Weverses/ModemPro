package com.weverses.modempro.util

import android.os.Build
import com.weverses.modempro.BuildConfig
import de.robv.android.xposed.XSharedPreferences
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers

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

    fun isSupportDevices(mDevice: Array<String>): Boolean {
        val device = Build.DEVICE
        for (str2 in mDevice) {
            if (device == str2) {
                return true
            }
        }
        return false
    }

    fun checkClassIfExists(className: String): Boolean {
        val exist = loadClassOrNull(className)
        return exist != null
    }

    fun checkMethodIfExists(className: String,methodName: String): Boolean {
        val exist = loadClass(className).methodFinder().filterByName(methodName).firstOrNull()
        return exist != null
    }

    fun getMethodDefReturn(className: String,methodName: String): String {
        return ""
    }

    fun hookMethodOfBoolean(className: String,methodName: String,Result: Boolean,Scope: String) {
        try {
            loadClass(className).methodFinder().first {
                name == methodName
            }.createHook{
                returnConstant(Result)
            }
            XposedBridge.log("ModemPro: Hook-${Scope} ${className}.${methodName} success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-${Scope} ${className}.${methodName} failed!")
            XposedBridge.log(e)
        }
    }

    fun hookMethodOfField(className: String,methodName: String,fieldName: String,Result: String,Scope: String) {
        try {
            loadClass(className).methodFinder().first {
                name == methodName
            }.createHook{
                before { param ->
                    param.thisObject.objectHelper().setObject(fieldName,Result)
                }
                XposedBridge.log("ModemPro: Hook-${Scope} ${className}.${methodName} ${fieldName} success!")
            }
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-${Scope} ${className}.${methodName} ${fieldName} failed!")
            XposedBridge.log(e)
        }
    }

    fun hookMethodOfArgs(className: String,methodName: String,Args: Int,Result: String,Scope: String) {
        try {
            loadClass(className).methodFinder().first {
                name == methodName
            }.createHook {
                before { param ->
                    param.args[Args] = Result
                }
            }
            XposedBridge.log("ModemPro: Hook-${Scope} ${className} ${methodName} ${Args} success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-${Scope} ${className} ${methodName} ${Args} failed!")
            XposedBridge.log(e)
        }
    }
}
