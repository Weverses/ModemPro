package com.weverses.modempro.hook.hooks.mtb

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Utils.hookMethodOfBoolean

object isUserBuild : BaseHook() {
    override fun init() {
        hookMethodOfBoolean(
            "com.xiaomi.mtb.MtbUtils",
            "isUserBuild",
            false,
            "mtb"
        )
    }
}
