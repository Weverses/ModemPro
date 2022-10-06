package com.weverses.modempro.hook.hooks.mtb

import com.github.kyuubiran.ezxhelper.utils.field
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object BypassAuthentication : BaseHook() {
    override fun init() {
        try {
            findMethod("com.xiaomi.mtb.MtbApp") {
                name == "setMiServerPermissionClass"
            }.hookBefore {
                it.args[0] = 0
                XposedBridge.log("ModemX55Pro: Hook mtb-setMiServerPermissionClass success!")
            }
        }catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook mtb-setMiServerPermissionClass failed!")
            XposedBridge.log(e)
        }

        try {
            findMethod("com.xiaomi.mtb.activity.ModemTestBoxMainActivity") {
                name == "updateClass"
            }.hookBefore {
                it.thisObject.field("mClassNet", true).set(it.thisObject, 0)
                XposedBridge.log("ModemX55Pro: Hook mtb-updateClass success!")
            }
        }catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook mtb-updateClass failed!")
            XposedBridge.log(e)
        }

        try {
            findMethod("com.xiaomi.mtb.activity.ModemTestBoxMainActivity") {
                name == "initClassProduct"
            }.hookAfter {
                it.thisObject.field("mClassProduct", true).set(it.thisObject, 0)
                XposedBridge.log("ModemX55Pro: Hook mtb-initClassProduct success!")
            }
        }catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook mtb-initClassProduct failed!")
            XposedBridge.log(e)
        }
    }
}