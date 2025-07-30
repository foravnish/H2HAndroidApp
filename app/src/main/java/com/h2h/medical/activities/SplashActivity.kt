package com.h2h.medical.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.h2h.medical.R
import com.h2h.medical.utils.AppPreferences

class SplashActivity : BaseActivity() {

    private lateinit var mAppPreferences: AppPreferences

    companion object {
        fun open(myActivity: Activity) {
            val intent = Intent(myActivity, SplashActivity::class.java)
            myActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mAppPreferences = AppPreferences.getInstance(this)

        Handler().postDelayed({
            if (mAppPreferences.getLogin())
                HomeActivity.open(this)
            else
                LoginActivity.open(this)
            finish()
        }, 1000)
    }
}
