package com.h2h.medical.activities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import com.h2h.medical.R
import com.h2h.medical.utils.ToastUtil


class ContactUsActivity : BaseActivity() {
    private lateinit var mTvEmail: TextView
    private lateinit var mTvPhone: TextView
    companion object {
        fun open(myActivity: Activity) {
            val intent = Intent(myActivity, ContactUsActivity::class.java)
            myActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        initViews()
        initListeners()
    }


    private fun initListeners() {

        mTvEmail.setOnClickListener {
            try {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "info@h2hmedical.com"))
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                ToastUtil.showErrorToast(this, "No email application found in your device.")
            }
        }

        mTvPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${R.string._385_374_1975}")
            startActivity(intent)
        }
    }

    private fun initViews() {
        mTvEmail = findViewById(R.id.tv_email)
        mTvPhone = findViewById(R.id.tv_phone)
    }
}