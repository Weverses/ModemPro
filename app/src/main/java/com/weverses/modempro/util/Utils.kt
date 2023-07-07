package com.weverses.modempro.util

import android.os.Build
import com.weverses.modempro.BuildConfig
import de.robv.android.xposed.XSharedPreferences
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers.*

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

    fun checkClassIfExists(Class: String,Method: String): Boolean {
        return try {
            findClassIfExists(Class, classLoader).methodFinder().first {
                name == Method
            }.createHook {
                // do nothing
            }
            true
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: ${Class}.${Method} doesn't exist")
            XposedBridge.log(e)
            false
        }
    }

    fun hookMethodOfBoolean(Class: String,Method: String,Result: Boolean,Scope: String) {
        try {
            loadClass(Class).methodFinder().first {
                name == Method
            }.createHook{
                returnConstant(Result)
            }
            XposedBridge.log("ModemPro: Hook-${Scope} ${Class} ${Method} success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-${Scope} ${Class} ${Method} failed!")
            XposedBridge.log(e)
        }
    }

    fun hookMethodOfField(Class: String,Method: String,Field: String,Result: String,Scope: String) {
        try {
            loadClass(Class).methodFinder().first {
                name == Method
            }.createHook{
                before { param ->
                    param.thisObject.objectHelper().setObject(Field,Result)
                }
                XposedBridge.log("ModemPro: Hook-${Scope} ${Class} ${Method} ${Field} success!")
            }
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-${Scope} ${Class} ${Method} ${Field} failed!")
            XposedBridge.log(e)
        }
    }

    fun hookMethodOfArgs(Class: String,Method: String,Args: Int,Result: String,Scope: String) {
        try {
            loadClass(Class).methodFinder().first {
                name == Method
            }.createHook {
                before { param ->
                    param.args[Args] = Result
                }
            }
            XposedBridge.log("ModemPro: Hook-${Scope} ${Class} ${Method} ${Args} success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook-${Scope} ${Class} ${Method} ${Args} failed!")
            XposedBridge.log(e)
        }
    }
}
