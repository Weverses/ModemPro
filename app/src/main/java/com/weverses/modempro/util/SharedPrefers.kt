package com.weverses.modempro.util

import android.content.Context
import cn.fkj233.ui.activity.MIUIActivity.Companion.context

object SharedPrefers {
    val sharedPreferences = context.getSharedPreferences("mSupport", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    fun setBoolean(key: String,boolean: Boolean){
        editor.putBoolean(key,boolean)
        editor.apply()
    }
}