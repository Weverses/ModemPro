package com.weverses.modempro.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object ApkSignatureVerifier : BaseHook() {
    override fun init() {
        try {
            findMethod("android.util.apk.ApkSignatureVerifier") {
                name == "getMinimumSignatureSchemeVersionForTargetSdk"
            }.hookBefore {
                it.result = 1
            }
            XposedBridge.log("ModemX55Pro: Hook getMinimumSignatureSchemeVersionForTargetSdk success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook getMinimumSignatureSchemeVersionForTargetSdk failed!")
            XposedBridge.log(e)
        }
    }
}
