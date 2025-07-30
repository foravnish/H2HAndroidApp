package com.h2h.medical.activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.android.volley.VolleyError
import com.h2h.medical.R
import com.h2h.medical.helper.HitApiHelper
import com.h2h.medical.utils.ApiResponse
import com.h2h.medical.utils.AppPreferences
import com.h2h.medical.utils.Constants
import com.h2h.medical.utils.ToastUtil
import org.json.JSONObject

class LoginActivity : BaseActivity(), HitApiHelper {

    private lateinit var mBtnLogin: Button
    private lateinit var mTvSignUp: TextView

    private lateinit var mEtUserName: EditText
    private lateinit var mEtPassword: EditText
    private lateinit var mLlForgotPassword: LinearLayout

    private lateinit var mAppPreferences: AppPreferences
    private lateinit var forgotPasswordDialog: Dialog

    companion object {
        fun open(myActivity: Activity) {
            val intent = Intent(myActivity, LoginActivity::class.java)
            myActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        listeners()
    }

    private fun listeners() {

        mTvSignUp.setOnClickListener {
            SignupActivity.open(this)
            finish()
        }

        mBtnLogin.setOnClickListener {
            if (valid()) {
                showLoader(this, "Signing In...")
                ApiResponse().hitVolleyApi(
                    this,
                    this,
                    getParams(Constants.USER_LOGIN, ""),
                    Constants.USER_LOGIN
                )
            } else {
                ToastUtil.showErrorToast(this, "Invalid username and password...")
            }
        }


        mLlForgotPassword.setOnClickListener {
            showDialog()
        }
    }


    private fun showDialog() {
        forgotPasswordDialog = Dialog(this)
        forgotPasswordDialog.setContentView(R.layout.dialog_forget_password)
        forgotPasswordDialog.setCancelable(false)
        val email: EditText = forgotPasswordDialog.findViewById(R.id.et_email)
        val cancel: TextView = forgotPasswordDialog.findViewById(R.id.cancel)
        val ok: TextView = forgotPasswordDialog.findViewById(R.id.ok)

        cancel.setOnClickListener { forgotPasswordDialog.dismiss() }

        ok.setOnClickListener {
            if (email.text.toString().trim() == "") {
                ToastUtil.showErrorToast(this, "Email required")
            } else {
                showLoader(this, "Restoring your password..")
                ApiResponse().hitVolleyApi(
                    this,
                    this,
                    getParams(Constants.FORGOT_PASSWORD, email.text.toString().trim()),
                    Constants.FORGOT_PASSWORD
                )
            }
        }

        val window = forgotPasswordDialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        forgotPasswordDialog.show()
    }


    private fun valid(): Boolean {
        return when {
            mEtUserName.text.toString().trim() == "" -> false
            mEtPassword.text.toString().trim() == "" -> false
            else -> true
        }
    }

    private fun initViews() {
        mBtnLogin = findViewById(R.id.btn_login)
        mTvSignUp = findViewById(R.id.tv_signup)
        mEtUserName = findViewById(R.id.et_user_name)
        mEtPassword = findViewById(R.id.et_password)
        mLlForgotPassword = findViewById(R.id.ll_forgot_password)

        mAppPreferences = AppPreferences.getInstance(this)
    }


    private fun getParams(type: String, forgotEmail: String): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        if (type == Constants.USER_LOGIN) {
            hashMap["email"] = "" + mEtUserName.text.toString().trim()
            hashMap["password"] = "" + mEtPassword.text.toString().trim()
        } else if (type == Constants.FORGOT_PASSWORD) {
            hashMap["email"] = "" + forgotEmail
        }
        return hashMap
    }


    override fun onResponse(response: String, type: String) {
        if (type == Constants.USER_LOGIN) {
            try {
                val jsonObject = JSONObject(response)
                if (jsonObject.getString("status") == "true") {
                    val mainObject: JSONObject = jsonObject.getJSONObject("data")
                    mAppPreferences.setUserId(mainObject.getString("user_id"))
                    mAppPreferences.setUserEmail(mainObject.getString("user_email"))
                    mAppPreferences.setUserName(mainObject.getString("user_fullname"))
                    mAppPreferences.setUserMobile(mainObject.getString("user_mob"))
                    mAppPreferences.setUserImage(mainObject.getString("user_img"))
                    mAppPreferences.setUserStatus(mainObject.getString("user_status"))
                    mAppPreferences.setLogin(true)
                    HomeActivity.open(this)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity()
                    } else {
                        finish()
                    }
                } else {
                    ToastUtil.showErrorToast(this, jsonObject.getString("message"))
                }
            } catch (exception: Exception) {
                exception.stackTrace
                Log.e(Constants.USER_LOGIN, exception.message.toString())
            } finally {
                dismissLoader()
            }
        } else if (type == Constants.FORGOT_PASSWORD) {
            try {
                val jsonObject = JSONObject(response)
                if (jsonObject.getString("status") == "true") {
                    ToastUtil.showSuccessToast(this, jsonObject.getString("message"))
                } else {
                    ToastUtil.showErrorToast(this, jsonObject.getString("message"))
                }
            } catch (exception: Exception) {
                exception.stackTrace
                Log.e(Constants.FORGOT_PASSWORD, exception.message.toString())
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
