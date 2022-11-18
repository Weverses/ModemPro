package com.weverses.modempro.activity

import android.annotation.SuppressLint
import android.os.Bundle
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import com.weverses.modempro.R
import com.weverses.modempro.util.Utils
import kotlin.system.exitProcess

class MainActivity : MIUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        checkLSPosed()
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

    init {
        initView {
            registerMain(getString(R.string.app_title), false) {
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
                        textId = R.string.n1_title,
                        tipsId = R.string.n1_summary),
                    SwitchV("n1_band", true)
                )
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
                        tipsId = R.string.n5_n8_summary),
                    SwitchV("n5_n8_band", true)
                )

                Line()
                TitleText(textId = R.string.title6)
                TextSummaryWithSwitch(
                    TextSummaryV(
                        textId = R.string.vice_slot_volte_title,
                        tipsId = R.string.vice_slot_volte_summary
                    ),
                    SwitchV("vice_slot_volte", true)
                )

                Line()
                TitleText(textId = R.string.title3)
                TextSummaryWithSwitch(
                    TextSummaryV(
                        textId = R.string.mtb_auth_title,
                        tipsId = R.string.mtb_auth_summary
                    ),
                    SwitchV("mtb_auth", false)
                )

                Line()
                TitleText(textId = R.string.title4)
                TextSummaryWithSwitch(
                    TextSummaryV(
                        textId = R.string.dual_sa_title,
                        tipsId = R.string.dual_sa_summary
                    ),
                    SwitchV("dual_sa", false)
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
