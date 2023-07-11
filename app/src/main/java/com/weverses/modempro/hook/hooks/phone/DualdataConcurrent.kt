package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import com.weverses.modempro.util.Utils.hookMethodOfField
import com.weverses.modempro.util.getPackageNames
import com.weverses.modempro.util.getPackageNames.getAllAppPackageNames
import de.robv.android.xposed.XposedBridge

// Platforms Support:
// sm8450/sm8475/sm7475/sm8550
// Framework/Telephone Services Support:
// xm13 series Only
object DualdataConcurrent : BaseHook() {
    override fun init() {
        // 这样应该就是直接使其返回空了吧，也许吧
        loadClass("com.android.phone.dsda.MiuiDualDataController")
            .methodFinder()
            .filterByName("initDualDataConcurrentModeWhiteList")
            .first()
            .createHook {
                before { param ->
                    null
                }
            }
        loadClass("com.android.phone.dsda.MiuiDualDataController")
            .methodFinder()
            .filterByName("updateDualDataConcurrentModeAppWhiteList")
            .first()
            .createHook {
                before { param ->
                    null
                }
            }
        XposedBridge.log("ModemPro: Hook-phone initDualDataConcurrentModeWhiteList success!")
        // 我也不知道为什么直接hook return true不可以
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