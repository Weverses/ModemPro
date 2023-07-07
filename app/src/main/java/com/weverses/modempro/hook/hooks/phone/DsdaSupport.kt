package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import com.weverses.modempro.util.Utils.isMTK
import de.robv.android.xposed.XposedBridge


object DsdaSupport : BaseHook() {
    override fun init() {
        if(isMTK()) {
            hookMethodOfBoolean(
                "com.android.phone.MiuiPhoneUtils",
                "isDSDASupported",
                true,
                "phone"
            )
        } else {
            hookMethodOfBoolean(
                "miui.telephony.TelephonyManagerEx",
                "isDsdaSupported",
                true,
                "phone"
            )

            hookMethodOfBoolean(
                "com.android.phone.MiuiPhoneUtils",
                "isDeviceDsdaSupportedByQcom",
                true,
                "phone"
            )
        }
    }
}

