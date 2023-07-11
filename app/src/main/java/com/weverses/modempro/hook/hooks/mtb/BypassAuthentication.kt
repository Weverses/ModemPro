package com.weverses.modempro.hook.hooks.mtb

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfArgs
import com.weverses.modempro.util.Utils.hookMethodOfField
import de.robv.android.xposed.XposedBridge

object BypassAuthentication : BaseHook() {
    override fun init() {
        hookMethodOfArgs(
            "com.xiaomi.mtb.MtbApp",
            "setMiServerPermissionClass",
            0,
            "0",
            "mtb"
        )

        try {
            ClassUtils.loadClass("com.xiaomi.mtb.activity.ModemTestBoxMainActivity").methodFinder()
                .first {
                    name == "updateClass"
                }.createHook {
                before { param ->
                    param.args[0] = 0
                    param.thisObject.objectHelper().setObject("mClassNet", 0)
                }
                XposedBridge.log("ModemPro: Hook mtb-updateClass success!")
            }
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook mtb-updateClass failed!")
            XposedBridge.log(e)
        }

        hookMethodOfField(
            "com.xiaomi.mtb.activity.ModemTestBoxMainActivity",
            "initClassProduct",
            "mClassProduct",
            "0",
            "mtb"
        )
    }
}