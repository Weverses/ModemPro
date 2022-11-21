package com.weverses.modempro.hook

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logexIfThrow
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.hook.hooks.android.*
import com.weverses.modempro.hook.hooks.mtb.BypassAuthentication
import com.weverses.modempro.hook.hooks.mtb.isUserBuild
import com.weverses.modempro.hook.hooks.phone.DualNrSupport
import com.weverses.modempro.hook.hooks.phone.N5N8BandPhone
import com.weverses.modempro.hook.hooks.phone.ViceSlotVolteButton
import com.weverses.modempro.util.Utils
import com.weverses.modempro.util.Utils.setMTBFeature
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val TAG = "ModemPro"
private val PACKAGE_NAME_HOOKED = setOf(
    "android", "com.android.phone", "com.xiaomi.mtb"
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
                "android" -> {
                    if (Utils.getBoolean("dual_nr", true)) {
                        initHooks(DualNRSupport)
                    }
                    if (Utils.getBoolean("dual_sa", true)) {
                        initHooks(DualSASupport)
                    }
                    if (Utils.getBoolean("n1_band", true)) {
                        initHooks(N1Band)
                    }
                    if (Utils.getBoolean("n28_band", true)) {
                        initHooks(N28Band)
                    }
                    if (Utils.getBoolean("n5_n8_band", true)) {
                        initHooks(N5N8Band)
                    }
                }
                "com.android.phone" -> {
                    if (Utils.getBoolean("dual_nr", true)) {
                        initHooks(DualNrSupport)
                    }
                    if (Utils.getBoolean("dual_sa", true)) {
                        initHooks(DualSaSupport)
                    }
                    if (Utils.getBoolean("n1_band", true)) {
                        initHooks(N1BandPhone)
                    }
                    if (Utils.getBoolean("n28_band", true)) {
                        initHooks(N28BandPhone)
                    }
                    if (Utils.getBoolean("n5_n8_band", true)) {
                        initHooks(N5N8BandPhone)
                    }
                    if (Utils.getBoolean("vice_slot_volte", true)) {
                        initHooks(ViceSlotVolteButton)
                    }
                }
                "com.xiaomi.mtb" -> {
                    if (setMTBFeature(mKey = true)) {
                        initHooks(BypassAuthentication)
                        initHooks(isUserBuild)
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