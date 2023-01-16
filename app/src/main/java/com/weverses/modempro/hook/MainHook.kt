package com.weverses.modempro.hook

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logexIfThrow
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.hook.misettings.NewRefreshRateFragment
import com.weverses.modempro.hook.powerkeeper.ParseCustomModeSwitchFromDb
import com.weverses.modempro.util.Utils
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val TAG = "ModemPro"
private val PACKAGE_NAME_HOOKED = setOf(
    "com.miui.powerkeeper", "com.xiaomi.misettings"
)

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName in PACKAGE_NAME_HOOKED) {
            // Init EzXHelper
            EzXHelperInit.initHandleLoadPackage(lpparam)
            EzXHelperInit.setLogTag(TAG)
            EzXHelperInit.setToastTag(TAG)
            // Init hooks
            when (lpparam.packageName) {
                "com.miui.powerkeeper" -> {
                    if (Utils.getBoolean("custom", true)) {
                        initHooks(ParseCustomModeSwitchFromDb)
                    }
                }
                "com.xiaomi.misettings" -> {
                    if (Utils.getBoolean("90hz", true)) {
                        initHooks(NewRefreshRateFragment)
                    }
                }
            }
        }
    }

    private fun initHooks(vararg hook: BaseHook) {
        hook.forEach {
            runCatching {
                if (it.isInit) return@forEach
                it.init()
                it.isInit = true
                Log.i("Inited hook: ${it.javaClass.simpleName}")
            }.logexIfThrow("Failed init hook: ${it.javaClass.simpleName}")
        }
    }
}
