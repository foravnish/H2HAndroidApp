package com.h2h.medical.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.VolleyError
import com.h2h.medical.R
import com.h2h.medical.helper.HitApiHelper
import com.h2h.medical.utils.ApiResponse
import com.h2h.medical.utils.AppPreferences
import com.h2h.medical.utils.Constants
import com.h2h.medical.utils.ToastUtil
import org.json.JSONObject

class SignupActivity : BaseActivity(), HitApiHelper {


    private lateinit var mBtnSignUp: Button
    private lateinit var mTvLogin: TextView

    private lateinit var mEtUserName: EditText
    private lateinit var mEtEmail: EditText
    private lateinit var mEtPhoneNumber: EditText
    private lateinit var mEtPassword: EditText
    private lateinit var mEtConfirmPassword: EditText
    private lateinit var mAppPreferences: AppPreferences

    companion object {
        fun open(myActivity: Activity) {
            val intent = Intent(myActivity, SignupActivity::class.java)
            myActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initViews()
        listeners()
    }

    private fun listeners() {

        mTvLogin.setOnClickListener {
            LoginActivity.open(this)
            finish()
        }

        mBtnSignUp.setOnClickListener {
            if (valid()) {
                showLoader(this, "Registering...")
                ApiResponse().hitVolleyApi(
                    this,
                    this,
                    getParams(Constants.USER_REGISTER),
                    Constants.USER_REGISTER
                )
            }
        }
    }

    private fun valid(): Boolean {
        return when {
            mEtUserName.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(this, "Name required")
                false
            }
            mEtEmail.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(this, "Email required")
                false
            }
            mEtPhoneNumber.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(this, "Phone number required")
                false
            }
            mEtPassword.text.toString().trim() == "" -> {
                ToastUtil.showErrorToast(this, "Password required")
                false
            }
            mEtPassword.text.toString().trim() != mEtConfirmPassword.text.toString().trim() -> {
                ToastUtil.showErrorToast(this, "Passwords doesn't match")
                false
            }
            else -> true
        }
    }


    private fun initViews() {
        mBtnSignUp = findViewById(R.id.btn_sign_up)
        mTvLogin = findViewById(R.id.tv_login)
        mEtUserName = findViewById(R.id.et_user_name)
        mEtEmail = findViewById(R.id.et_email)
        mEtPhoneNumber = findViewById(R.id.et_phone_number)
        mEtPassword = findViewById(R.id.et_password)
        mEtConfirmPassword = findViewById(R.id.et_confirm_password)

        mAppPreferences = AppPreferences.getInstance(this)
    }

    private fun getParams(type: String): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        if (type == Constants.USER_REGISTER) {
            hashMap["user_fullname"] = mEtUserName.text.toString().trim()
            hashMap["user_email"] = mEtEmail.text.toString().trim()
            hashMap["password"] = mEtPassword.text.toString().trim()
            hashMap["user_mob"] = mEtPhoneNumber.text.toString().trim()
            hashMap["user_status"] = "Active"
        }
        return hashMap
    }

    override fun onResponse(response: String, type: String) {
        if (type == Constants.USER_REGISTER) {
            try {
                val jsonObject = JSONObject(response)
                if (jsonObject.getString("status") == "true") {
//                    val mainObject: JSONObject = jsonObject.getJSONObject("data")
//                    mAppPreferences.setUserId(mainObject.getString("user_id"))
//                    mAppPreferences.setUserEmail(mainObject.getString("user_email"))
//                    mAppPreferences.setUserName(mainObject.getString("user_fullname"))
//                    mAppPreferences.setUserMobile(mainObject.getString("user_mob"))
//                    try {
//                        mAppPreferences.setUserImage(mainObject.getString("user_img"))
//                    } catch (e: Exception) {
//                    }
//                    mAppPreferences.setUserStatus(mainObject.getString("user_status"))
//                    mAppPreferences.setLogin(true)

                    AlertDialog.Builder(this@SignupActivity)
                        .setTitle("Success")
                        .setCancelable(false)
                        .setMessage("Signup Successfully.")
                        .setPositiveButton(
                            "Continue"
                        ) { _, _ ->
                            LoginActivity.open(this)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity()
                            } else {
                                finish()
                            }

                        }
                        .show()

                } else {
                    ToastUtil.showErrorToast(this, jsonObject.getString("message"))
                }
            } catch (exception: Exception) {
                exception.stackTrace
                Log.e(Constants.USER_REGISTER, exception.message.toString())
            } finally {
                dismissLoader()
            }
        }
    }

    override fun onError(e: VolleyError) {
        ToastUtil.showErrorToast(this, "Couldn't connect to server")
        dismissLoader()
    }
}
