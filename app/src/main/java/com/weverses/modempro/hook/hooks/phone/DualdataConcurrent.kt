package com.weverses.modempro.hook.hooks.phone

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.weverses.modempro.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

// Platforms Support:
// sm8450/sm8475/sm7475/sm8550
// Framework/Telephone Services Support:
// xm13 series Only
object DualdataConcurrent : BaseHook() {
    override fun init() {
        try {
            loadClass("com.android.phone.dsda.MiuiDualDataController").methodFinder().first {
                name == "isConcurrentWhiteListApp"
            }.createHook{
                returnConstant(true)
            }
            XposedBridge.log("ModemX55Pro: Hook-phone isConcurrentWhiteListApp success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone isConcurrentWhiteListApp failed!")
            XposedBridge.log(e)
        }
        try {
            loadClass("com.android.phone.dsda.MiuiDualDataController").methodFinder().first {
                name == "isRedundantWhiteListApp"
            }.createHook{
                returnConstant(false)
            }
            XposedBridge.log("ModemX55Pro: Hook-phone isRedundantWhiteListApp success!")
        } catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone isRedundantWhiteListApp failed!")
            XposedBridge.log(e)
        }
        try {
            ClassUtils.loadClass("com.android.phone.dsda.MiuiDualDataController").methodFinder().first {
                name == "processForegroundInfoEvent"
            }.createHook{
                before { param ->
                    param.thisObject.objectHelper().setObject("mConcurrentWhiteListForground", true)
                }
                XposedBridge.log("ModemX55Pro: Hook-phone mConcurrentWhiteListForground success!")
            }
        }catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone mConcurrentWhiteListForground failed!")
            XposedBridge.log(e)
        }
        try {
            ClassUtils.loadClass("com.android.phone.dsda.MiuiDualDataController").methodFinder().first {
                name == "processForegroundInfoEvent"
            }.createHook{
                before { param ->
                    param.thisObject.objectHelper().setObject("mRedundantWhiteListForground", false)
                }
                XposedBridge.log("ModemX55Pro: Hook-phone mRedundantWhiteListForground success!")
            }
        }catch (e: Throwable) {
            XposedBridge.log("ModemX55Pro: Hook-phone mRedundantWhiteListForground failed!")
            XposedBridge.log(e)
        }
    }
}