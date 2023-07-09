package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import com.weverses.modempro.util.Utils.hookMethodOfField
import de.robv.android.xposed.XposedBridge

// Platforms Support:
// sm8450/sm8475/sm7475/sm8550
// Framework/Telephone Services Support:
// xm13 series Only
object DualdataRedundant : BaseHook() {
    override fun init() {
        // 这样应该就是直接使其返回空了吧，也许吧
        ClassUtils.loadClass("com.android.phone.dsda.MiuiDualDataController")
            .methodFinder()
            .filterByName("initDualDataRedundantModeWhiteList")
            .first()
            .createHook {
                before { param ->
                    null
                }
            }
        ClassUtils.loadClass("com.android.phone.dsda.MiuiDualDataController")
            .methodFinder()
            .filterByName("updateDualDataRedundantModeAppWhiteList")
            .first()
            .createHook {
                before { param ->
                    null
                }
            }
        XposedBridge.log("ModemPro: Hook-phone initDualDataRedundantModeWhiteList success!")
        /*
        hookMethodOfBoolean(
            "com.android.phone.dsda.MiuiDualDataController",
            "isConcurrentWhiteListApp",
            true,
            "phone"
        )
        hookMethodOfBoolean(
            "com.android.phone.dsda.MiuiDualDataController",
            "isRedundantWhiteListApp",
            false,
            "phone"
        )
        hookMethodOfField(
            "com.android.phone.dsda.MiuiDualDataController",
            "processForegroundInfoEvent",
            "mConcurrentWhiteListForground",
            "true",
            "phone"
        )
        hookMethodOfField(
            "com.android.phone.dsda.MiuiDualDataController",
            "processForegroundInfoEvent",
            "mRedundantWhiteListForground",
            "false",
            "phone"
        )
       */
    }
}