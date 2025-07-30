package com.h2h.medical.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import com.h2h.medical.R
import com.h2h.medical.utils.Constants

class WebViewActivity : BaseActivity() {

    private lateinit var mTvHeaderTitle: TextView
    private lateinit var mWebView: WebView

    companion object {
        fun open(myActivity: Activity, from: String) {
            val intent = Intent(myActivity, WebViewActivity::class.java)
            intent.putExtra("from", from)
            myActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        initViews()
        setData()
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        if (intent.hasExtra("from")) {
            if (intent.getStringExtra("from") == "privacyPolicy") {
                mTvHeaderTitle.text = "Privacy Policy"
                mWebView.loadUrl(Constants.PRIVACY_POLICY_URL)
            } else if (intent.getStringExtra("from") == "terms") {
                mTvHeaderTitle.text = "Terms and Conditions"
                mWebView.loadUrl(Constants.TERMS_AND_CONDITIONS_URL)
            }
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        mTvHeaderTitle = findViewById(R.id.tv_header_title)
        mWebView = findViewById(R.id.web_view)
        mWebView.settings.javaScriptEnabled = true
    }
}