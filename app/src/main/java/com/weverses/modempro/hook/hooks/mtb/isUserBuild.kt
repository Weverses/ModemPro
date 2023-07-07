package com.weverses.modempro.hook.hooks.mtb

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object isUserBuild : BaseHook() {
    override fun init() {
        try {
            ClassUtils.loadClass("com.xiaomi.mtb.MtbUtils").methodFinder().first {
                name == "isUserBuild"
            }.createHook{
                returnConstant(false)
            }
            XposedBridge.log("ModemPro: Hook mtb-isUserBuild success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook mtb-isUserBuild failed!")
            XposedBridge.log(e)
        }
    }
}
