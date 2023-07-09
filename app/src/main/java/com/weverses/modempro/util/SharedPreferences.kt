package com.weverses.modempro.util

import android.content.Context
import android.content.SharedPreferences
import cn.fkj233.ui.activity.data.SafeSharedPreferences

object SharedPreferences {
    fun android.content.SharedPreferences.putStringSet(
        key: String,
        value: Set<String>
    ) {
        val editor = edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    var safeSP: SafeSharedPreferences = SafeSharedPreferences()

    var mSP: SharedPreferences? = null

    fun setSP(sharedPreferences: SharedPreferences) {
        safeSP.mSP = sharedPreferences
    }

    /**
     *  获取 SharedPreferences / Get SharedPreferences
     *  @return: SharedPreferences
     */
    @Suppress("unused")
    fun getSP(): SharedPreferences? {
        return safeSP.mSP
    }

    fun putAny(key: String, any: Any) {
        if (mSP == null) return
        mSP!!.edit().apply {
            when (any) {
                is Boolean -> putBoolean(key, any)
                is String ->  putString(key, any)
                is Int -> putInt(key, any)
                is Float -> putFloat(key, any)
                is Long -> putLong(key, any)
            }
            apply()
        }
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return if (mSP == null) {
            defValue
        } else {
            mSP!!.getBoolean(key, defValue)
        }
    }

    fun setBoolean(key: String, boolean: Boolean) {
        putAny(key,boolean)
    }
}