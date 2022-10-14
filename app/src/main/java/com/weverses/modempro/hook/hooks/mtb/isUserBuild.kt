package com.weverses.modempro.hook.hooks.mtb

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object isUserBuild : BaseHook() {
    override fun init() {
        try {
            findMethod("com.xiaomi.mtb.MtbUtils") {
                name == "isUserBuild"
            }.hookReturnConstant(false)
            XposedBridge.log("ModemX55Pro: Hook mtb-isUserBuild success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook mtb-isUserBuild failed!")
            XposedBridge.log(e)
        }
    }
}
