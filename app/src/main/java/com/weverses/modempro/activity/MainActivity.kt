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
import com.weverses.modempro.util.Utils
import com.weverses.modempro.util.Utils.getPlatform
import com.weverses.modempro.util.Utils.isDualdataSupportDevices
import com.weverses.modempro.util.Utils.isKonaPlatform
import com.weverses.modempro.util.Utils.isMTK
import com.weverses.modempro.util.Utils.isUnSupportedMIUIVersion
import com.weverses.modempro.util.Utils.islahainaPlatform
import kotlin.system.exitProcess

class MainActivity : MIUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        checkLSPosed()
        checkMTK()
        checkMIUIVersion()
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
                if (!isMTK() && !isUnSupportedMIUIVersion() && isKonaPlatform()) {
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


                if (!isUnSupportedMIUIVersion()) {
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


                Line()
                TitleText(textId = R.string.title4)
                if (islahainaPlatform()) {
                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.dual_sa_title,
                            tipsId = R.string.dual_sa_summary
                        ),
                        SwitchV("dual_sa", false)
                    )
                }

                if (isDualdataSupportDevices()) {
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

                    if (Utils.getBoolean("redundant", false)) {
                        TextSummaryWithSwitch(
                            TextSummaryV(
                                textId = R.string.concurrent_title,
                                tipsId = R.string.concurrent_summary
                            ),
                            SwitchV("concurrent", false)
                        )
                    }

                    if (Utils.getBoolean("concurrent", false)) {
                        TextSummaryWithSwitch(
                            TextSummaryV(
                                textId = R.string.redundant_title,
                                tipsId = R.string.redundant_summary
                            ),
                            SwitchV("redundant", false)
                        )
                    }


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
