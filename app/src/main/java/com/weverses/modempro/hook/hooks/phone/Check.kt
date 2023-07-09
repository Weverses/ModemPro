package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object Check: BaseHook() {
    // 因为在模块设置页时无法获得宿主的classloader，只能先hook一下宿主了:)
    val mDualdata = null
    override fun init() {
        val mDualdata = loadClassOrNull("com.android.phone.dsda.MiuiDualDataController")
        XposedBridge.log("ModemPro: ${mDualdata}")
    }

}