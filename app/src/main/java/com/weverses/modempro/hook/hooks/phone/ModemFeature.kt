package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils
import com.weverses.modempro.util.Utils.hookMethodOfArgs
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import de.robv.android.xposed.XposedBridge

object ModemFeature : BaseHook() {
    override fun init() {
        hookMethodOfArgs(
            "com.android.phone.FiveGManagerBase",
            "getModemFeatureMode",
            0,
            "-1",
            "mtb"
        )
        hookMethodOfBoolean(
            "com.android.phone.FiveGManagerBase",
            "getModemFeatureMode",
            true,
            "mtb"
        )

        hookMethodOfArgs(
            "com.android.phone.MiuiPhoneUtils",
            "isModemFeatureSupported",
            0,
            "-1",
            "mtb"
        )
        hookMethodOfArgs(
            "com.android.phone.FiveGManagerBase",
            "getModemFeatureFromDb",
            0,
            "-1",
            "mtb"
        )
    }
}
