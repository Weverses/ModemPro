package com.weverses.modempro.hook.hooks.mtb

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder

import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers.setStaticBooleanField

object BypassAuthentication : BaseHook() {
    override fun init() {
        try {
            ClassUtils.loadClass("com.xiaomi.mtb.MtbApp").methodFinder().first {
                name == "setMiServerPermissionClass"
            }.createHook{
                before { param ->
                    param.args[0] = 0
                }
            }
            XposedBridge.log("ModemX55Pro: Hook mtb-setMiServerPermissionClass success!")
        }catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook mtb-setMiServerPermissionClass failed!")
            XposedBridge.log(e)
        }

        try {
            ClassUtils.loadClass("com.xiaomi.mtb.activity.ModemTestBoxMainActivity").methodFinder().first {
                name == "updateClass"
            }.createHook{
                before { param ->
                    param.args[0] = 0
                    param.thisObject.objectHelper().setObject("mClassNet", 0)
                }
                XposedBridge.log("ModemX55Pro: Hook mtb-updateClass success!")
            }
        }catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook mtb-updateClass failed!")
            XposedBridge.log(e)
        }

        try {
            ClassUtils.loadClass("com.xiaomi.mtb.activity.ModemTestBoxMainActivity").methodFinder().first {
                name == "initClassProduct"
            }.createHook{
                before { param ->
                    param.thisObject.objectHelper().setObject("mClassProduct", 0)
                }
                XposedBridge.log("ModemX55Pro: Hook mtb-initClassProduct success!")
            }
        }catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook mtb-initClassProduct failed!")
            XposedBridge.log(e)
        }
    }
}