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
        try {
            ClassUtils.loadClass("com.xiaomi.mtb.XiaoMiServerPermissionCheck").methodFinder()
                .first {
                    name == "updatePermissionClass"
                }.createHook {
                    after {
                        it.result = 0L
                    }
                    XposedBridge.log("ModemPro: Hook mtb-setMiServerPermissionClass success!")
                }
        } catch (e: Throwable) {
            XposedBridge.log("ModemPro: Hook mtb-setMiServerPermissionClass failed!")
            XposedBridge.log(e)
        }
    }
}