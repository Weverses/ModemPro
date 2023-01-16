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
import com.weverses.modempro.util.Utils.isMTK
import com.weverses.modempro.util.Utils.isUnSupportedMIUIVersion
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
                if (!isMTK() && !isUnSupportedMIUIVersion()) {
                    TitleText(textId = R.string.refreshrate)
                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.custom_title,
                            tipsId = R.string.custom_summary
                        ),
                        SwitchV("cust", true)
                    )

                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.global_title,
                            tipsId = R.string.global_summary
                        ),
                        SwitchV("global", true)
                    )

                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.smartfps_title,
                            tipsId = R.string.smartfps_summary
                        ),
                        SwitchV("smartfps", true)
                    )

                    TextSummaryWithSwitch(
                        TextSummaryV(
                            textId = R.string.oldHz_title,
                            tipsId = R.string.oldHz_summary
                        ),
                        SwitchV("90hz", true)
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
                TitleText(textId = R.string.more)
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
