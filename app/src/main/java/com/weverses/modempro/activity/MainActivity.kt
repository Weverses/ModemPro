package com.weverses.modempro.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import com.weverses.modempro.R
import com.weverses.modempro.util.Check.DualdataDevices
import com.weverses.modempro.util.Check.isKonaPlatform
import com.weverses.modempro.util.Check.isMTK
import com.weverses.modempro.util.Check.isUnSupportedMIUIVersion
import com.weverses.modempro.util.Check.islahainaPlatform
import com.weverses.modempro.util.Utils
import com.weverses.modempro.util.Utils.isSupportDevices
import com.weverses.modempro.util.getPackageNames
import kotlin.system.exitProcess

class MainActivity : MIUIActivity() {
    // 根据默认是否支持来显示开关，不会有人想关功能的吧:)
    // byd 先不启用
    val isN5Support = false
    val isN8Support = false
    val isN28Support = false
    val NR = false
    val SA = false
    override fun onCreate(savedInstanceState: Bundle?) {
        checkLSPosed()
        checkMTK()
        checkMIUIVersion()
        val packageManager = applicationContext.packageManager
        val allPackageNames = getPackageNames.getAllAppPackageNames(packageManager)
        super.onCreate(savedInstanceState)
    }


    @SuppressLint("WorldReadableFiles")
    private fun checkLSPosed() {
        try {
            setSP(getSharedPreferences("config", MODE_WORLD_READABLE))
        } catch (exception: SecurityException) {
            isLoad = false
            MIUIDialog(this) {
                setTitle(R.string.warning)
                setMessage(R.string.not_support)
                setCancelable(false)
                setRButton(R.string.done) {
                    exitProcess(0)
                }
            }.show()
        }
    }

    private fun checkMTK() {
        if (isMTK()) {
            MIUIDialog(this) {
                setTitle(R.string.tips)
                setMessage(R.string.mtk_device)
                setCancelable(false)
                setRButton(R.string.done) {
                    dismiss()
                }
            }.show()
        }
    }

    private fun checkMIUIVersion() {
        if (isUnSupportedMIUIVersion()) {
            MIUIDialog(this) {
                setTitle(R.string.tips)
                setMessage(R.string.antique_version)
                setCancelable(false)
                setRButton(R.string.done) {
                    dismiss()
                }
            }.show()
        }
    }

    init {
        initView {
            registerMain(getString(R.string.app_title), false) {
                TitleText(textId = R.string.title_tips)
                if (isKonaPlatform() && !isUnSupportedMIUIVersion()) {
                    TitleText(textId = R.string.title1)
                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.dual_nr_title,
                            tipsId = R.string.dual_nr_summary
                        ),
                        SwitchV("dual_nr", true)
                    )

                    Line()
                    TitleText(textId = R.string.title2)
                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.n28_title,
                            tipsId = R.string.n28_summary
                        ),
                        SwitchV("n28_band", true)
                    )
                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.n5_n8_title,
                            tipsId = R.string.n5_n8_summary
                        ),
                        SwitchV("n5_n8_band", true)
                    )
                }

                if (!isSupportDevices(DualdataDevices) && !isUnSupportedMIUIVersion()) {
                    Line()
                    TitleText(textId = R.string.title6)
                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.smart_dual_sim_title,
                            tipsId = R.string.smart_dual_sim_summary
                        ),
                        SwitchV("vice_slot_volte", true)
                    )

                    TextA(
                        textId = R.string.vice_slot_volte_title,
                        onClickListener = {
                            MIUIDialog(this@MainActivity) {
                                setTitle(R.string.vice_slot_volte_title)
                                setMessage(R.string.vice_slot_volte_summary)
                                setLButton(R.string.disable) {
                                    Utils.exec("settings put global vice_slot_volte_data_enabled 0")
                                    dismiss()
                                }
                                setRButton(R.string.enable) {
                                    Utils.exec("settings put global vice_slot_volte_data_enabled 1")
                                    dismiss()
                                }
                            }.show()
                        }
                    )
                }

                if (!isMTK()) {
                    Line()
                    TitleText(textId = R.string.title3)
                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.mtb_auth_title,
                            tipsId = R.string.mtb_auth_summary
                        ),
                        SwitchV("mtb_auth", false)
                    )
                }

                if (islahainaPlatform()) {
                    Line()
                    TitleText(textId = R.string.title4)
                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.dual_sa_title,
                            tipsId = R.string.dual_sa_summary
                        ),
                        SwitchV("dual_sa", false)
                    )
                }

                // val mDualData = sharedPreferences.getBoolean("mDualData", false)
                if (isSupportDevices(DualdataDevices)){
                    Line()
                    TitleText(textId = R.string.title8)
                    TitleText(textId = R.string.title8_tips)
                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.smart_dual_data_title,
                            tipsId = R.string.smart_dual_data_summary
                        ),
                        SwitchV("dual_data", false)
                    )

                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.opt_title,
                            tipsId = R.string.opt_summary
                        ),
                        SwitchV("opt", false)
                    )

                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.hiking_title,
                            tipsId = R.string.hiking_summary
                        ),
                        SwitchV("hiking_city", false)
                    )

                    TextA(
                        textId = R.string.concurrent_title,
                        onClickListener = {
                            MIUIDialog(this@MainActivity) {
                                setTitle(R.string.tips)
                                setMessage(R.string.concurrent_summary)
                                setLButton(R.string.disable) {
                                    Utils.exec("settings put global dual_data_concurrent_mode_white_list_pkg_name com.android.providers.downloads.ui,com.ss.android.ugc.aweme,com.android.providers.downloads,tv.danmaku.bili,com.xiaomi.market,org.zwanoo.android.speedtest")
                                    safeSP.putAny("concurrent","false")
                                    dismiss()
                                }
                                setRButton(R.string.enable) {
                                    val packageManager = applicationContext.packageManager
                                    val allPackageNames = getPackageNames.getAllThirdPartyAppPackageNames(packageManager)
                                    Utils.exec("settings put global dual_data_concurrent_mode_white_list_pkg_name ${allPackageNames}")
                                    safeSP.putAny("concurrent","true")
                                    dismiss()
                                }
                            }.show()
                        }
                    )

                    TextA(
                        textId = R.string.redundant_title,
                        onClickListener = {
                            MIUIDialog(this@MainActivity) {
                                setTitle(R.string.tips)
                                setMessage(R.string.redundant_summary)
                                setLButton(R.string.disable) {
                                    Utils.exec("settings put global dual_data_redundant_mode_white_list_pkg_name com.tencent.tmgp.sgame,com.tencent.tmgp.pubgmhd")
                                    safeSP.putAny("redundant","true")
                                    dismiss()
                                }
                                setRButton(R.string.enable) {
                                    val packageManager = applicationContext.packageManager
                                    val allPackageNames = getPackageNames.getAllThirdPartyAppPackageNames(packageManager)
                                    Utils.exec("settings put global dual_data_redundant_mode_white_list_pkg_name ${allPackageNames}")
                                    safeSP.putAny("redundant","true")
                                    dismiss()
                                }
                            }.show()
                        }
                    )

                    Line()
                    TitleText(textId = R.string.title4)

                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.dual_data_bands_title,
                            tipsId = R.string.dual_data_bands_summary
                        ),
                        SwitchV("dualdata_bands", false)
                    )
                }

                Line()
                TitleText(textId = R.string.about)
                Author(
                    getDrawable(R.drawable.author)!!, "Weverse", "Hook,Icon...more" ,
                    onClickListener = {
                        MIUIDialog(this@MainActivity) {
                            setTitle(R.string.author_title)
                            setMessage(R.string.love)
                            setLButton(R.string.author_description) {
                                val uri = Uri.parse("http://www.coolapk.com/u/18895441")
                                val author = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(author)
                            }
                            setRButton(R.string.love_url) {
                                val love = Uri.parse("https://afdian.net/a/Weverse")
                                val intent = Intent(Intent.ACTION_VIEW, love)
                                startActivity(intent)
                            }
                        }.show()
                    }
                )

                Line()
                TitleText(textId = R.string.title5)
                TextA(
                    textId = R.string.reboot_system,
                    onClickListener = {
                        MIUIDialog(this@MainActivity) {
                            setTitle(R.string.warning)
                            setMessage(R.string.reboot_tips)
                            setLButton(R.string.cancel) {
                                dismiss()
                            }
                            setRButton(R.string.done) {
                                Utils.exec("/system/bin/sync;/system/bin/svc power reboot || reboot")
                            }
                        }.show()
                    }
                )
            }
        }
    }
}
