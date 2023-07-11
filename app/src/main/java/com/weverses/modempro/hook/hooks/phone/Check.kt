package com.weverses.modempro.hook.hooks.phone

import com.weverses.modempro.hook.hooks.BaseHook
import com.weverses.modempro.util.Check

object Check: BaseHook() {
    // 因为在模块设置页时无法获得宿主的classloader，只能先hook一下宿主了:)
    val mDualdata = null
    override fun init() {
    }

}