package com.weverses.modempro.hook.hooks

abstract class BaseHook {
    var isInit: Boolean = false
    abstract fun init()
}
