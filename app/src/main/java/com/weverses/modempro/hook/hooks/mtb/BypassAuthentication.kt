package com.weverses.modempro.hook.hooks.mtb

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Check.getAndroidVersion
import com.weverses.modempro.util.Check.getMIUIVersion
import com.weverses.modempro.util.Utils.hookMethodOfBoolean
import com.weverses.modempro.util.Utils.hookMethodOfLong
import com.weverses.modempro.util.Utils.hookMethodOfString

object BypassAuthentication : BaseHook() {
    override fun init() {
        hookMethodOfLong(
            "com.xiaomi.mtb.XiaoMiServerPermissionCheck",
            "updatePermissionClass",
            0L,
            "mtb"
        )
        // 在HyperOS上
        if (getMIUIVersion() > 14f || getAndroidVersion() > "13"){
            hookMethodOfString(
                "com.xiaomi.mtb.XiaoMiServerPermissionCheck",
                "getClassErrorString",
                "null",
                "mtb"
            )
            hookMethodOfBoolean(
                "com.xiaomi.modem.ModemUtils",
                "isUserBuild",
                false,
                "mtb"
            )
        }
        // 在Android 12上
        if (getAndroidVersion() < "13") {
            hookMethodOfBoolean(
                "com.xiaomi.mtb.MtbUtils",
                "isUserBuild",
                false,
                "mtb"
            )
        }
    }
}