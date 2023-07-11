package com.weverses.modempro.util

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import cn.fkj233.ui.activity.MIUIActivity.Companion.context

object getPackageNames {
    fun getAllAppPackageNames(packageManager: PackageManager): String {
        val packages = packageManager.getInstalledPackages(0)
        val packageNames = mutableListOf<String>()

        for (packageInfo in packages) {
            packageInfo.packageName?.let {
                packageNames.add(it)
            }
        }
        return packageNames.joinToString(",")
    }

    fun getAllThirdPartyAppPackageNames(packageManager: PackageManager): String {
        val packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        val thirdPartyPackageNames = mutableListOf<String>()

        for (packageInfo in packages) {
            if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val packageName = packageInfo.packageName
                if (packageName != null) {
                    thirdPartyPackageNames.add(packageName)
                }
            }
        }

        return thirdPartyPackageNames.joinToString(",")
    }

    /*
    private lateinit var appInfoList: MutableList<MyAppInfo>

    /**
     * 数据类, 存储应用信息
     *
    data class MyAppInfo(
        val appName: String,
        val packageName: String,
        val isSystemApp: Boolean
    )

    /**
     * 获取应用信息列表
     *
     * @return [MutableList] 应用信息列表
     */
    fun getAppInfoList(): MutableList<MyAppInfo> {
        if (::appInfoList.isInitialized)
            return appInfoList.apply {
                sortBy { it.appName }
            }.toMutableList()
        appInfoList = mutableListOf()
        val pm = context.packageManager
        for (appInfo in pm.getInstalledApplications(0)) {
            MyAppInfo(
                appInfo.loadLabel(pm).toString(),
                appInfo.packageName,
                appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
            ).also { appInfoList.add(it) }
        }
        return appInfoList.apply {
            sortBy { it.appName }
        }.toMutableList()
    }

     */
     */
}
