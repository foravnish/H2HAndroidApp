package com.h2h.medical.activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.h2h.medical.R
import com.h2h.medical.Volley.FileUploadModel
import com.h2h.medical.Volley.UpdateMultipartListener
import com.h2h.medical.Volley.VolleyMultipartRequest
import com.h2h.medical.Volley.onUpdateViewListener
import com.h2h.medical.helper.HitApiHelper
import com.h2h.medical.helper.PermissionHelper
import com.h2h.medical.utils.ApiResponse
import com.h2h.medical.utils.AppPreferences
import com.h2h.medical.utils.Constants
import com.h2h.medical.utils.ToastUtil
//import com.theartofdev.edmodo.cropper.CropImage
//import com.theartofdev.edmodo.cropper.CropImageView
import org.json.JSONObject
import java.io.File
import java.net.URI

class EditProfileActivity : BaseActivity(), HitApiHelper {

    private lateinit var mIvUserProfile: ImageView
    private lateinit var mRlEditProfile: RelativeLayout
    private lateinit var mEtName: EditText
    private lateinit var mEtEmail: EditText
    private lateinit var mEtPhone: EditText
    private lateinit var mTvChangePassword: TextView
    private lateinit var mBtnSave: Button

    private lateinit var mPermissionHelper: PermissionHelper
    private lateinit var permissions: Array<String>
    private var imagePath = ""
    private var photo: File = File("")

    private lateinit var mAppPreferences: AppPreferences
    private lateinit var changePasswordDialog: Dialog

    companion object {
        fun open(myActivity: Activity) {
            val intent = Intent(myActivity, EditProfileActivity::class.java)
            myActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initViews()
        listeners()
        setData()
    }


    private fun setData() {

        mEtName.setText(mAppPreferences.getUserName())
        mEtEmail.setText(mAppPreferences.getUserEmail())
        mEtPhone.setText(mAppPreferences.getUserMobile())

        if (mAppPreferences.getUserImage() != "") {
            Glide.with(this)
                .load(Constants.IMAGE_URL + mAppPreferences.getUserImage())
                .apply(RequestOptions().circleCrop())
                .into(mIvUserProfile)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_profile)
                .apply(RequestOptions().circleCrop())
                .into(mIvUserProfile)
        }
    }


    private fun listeners() {

        mIvUserProfile.setOnClickListener {
//            if (!mPermissionHelper.isPermissionGranted(android.Manifest.permission.READ_EXTERNAL_STORAGE) || !mPermissionHelper.isPermissionGranted(
//                    android.Manifest.permission.CAMERA
//                )
//            ) {
//                mPermissionHelper.requestPermission(
//                    permissions,
//                    "Storage permission required for uploading profile picture."
//                )
//            } else {
                /*CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this)*/
//            }
        }

        mRlEditProfile.setOnClickListener {
            mIvUserProfile.performClick()
        }

        mTvChangePassword.setOnClickListener {
            showChangePasswordDialog()
        }

        mBtnSave.setOnClickListener {
            if (valid()) {
                showLoader(this, "Saving your profile...")
                updateProfile()
            }
        }
    }

    private fun updateProfile() {

//        val volleyMultipartRequest = VolleyMultipartRequest(
//            Constants.BASE_URL + Constants.UPDATE_PROFILE,
//            object :
//                UpdateMultipartListener(this, object : onUpdateViewListener {
//                    override fun updateView(responseObject: Any, isSuccess: Boolean, reqType: Int) {
//                        if (isSuccess) {
//                            try {
//                                val jsonObject = JSONObject(responseObject.toString())
//                                if (jsonObject.getString("status") == "true") {
//                                    val mainObject = jsonObject.getJSONObject("data")
//                                    mAppPreferences.setUserMobile(mainObject.getString("user_mob"))
//                                    mAppPreferences.setUserName(mainObject.getString("user_fullname"))
//                                    if (mAppPreferences.getUserImage() == "")
//                                        mAppPreferences.setUserImage(mainObject.getString("user_img"))
//                                    else {
//                                        if (mainObject.getString("user_img") != "") {
//                                            mAppPreferences.setUserImage(mainObject.getString("user_img"))
//                                        }
//                                    }
//
//                                    ToastUtil.showSuccessToast(
//                                        this@EditProfileActivity,
//                                        "Profile updated successfully."
//                                    )
//                                    HomeActivity.open(this@EditProfileActivity)
//                                    finish()
//                                } else {
//                                    ToastUtil.showErrorToast(
//                                        this@EditProfileActivity,
//                                        jsonObject.getString("message")
//                                    )
//                                }
//                            } catch (e: java.lang.Exception) {
//                                e.message
//                                e.printStackTrace()
//                            } finally {
//                                dismissLoader()
//                            }
//                        } else {
//                            dismissLoader()
//                            ToastUtil.showErrorToast(
//                                this@EditProfileActivity,
//                                responseObject.toString()
//                            )
//                        }
//                    }
//                }, 1000) {
//            },
//            FileUploadModel::class.java,
//            "image",
//            photo,
//            getParams(Constants.UPDATE_PROFILE)
//        )
//        val requestQueue = Volley.newRequestQueue(applicationContext)
//        requestQueue.add(volleyMultipartRequest)


        showLoader(this, "Saving...")
        ApiResponse().hitVolleyApi(
            this,
            this,
            getParams(Constants.UPDATE_PROFILE),
            Constants.UPDATE_PROFILE
        )
    }


    private fun valid(): Boolean {
        if (mEtName.text.toString().trim() == "") {
            ToastUtil.showErrorToast(this, "Name required")
            return false
        } else if (mEtPhone.text.toString().trim() == "") {
            ToastUtil.showErrorToast(this, "Phone number required")
            return false
        } else
            return true
    }


    private fun initViews() {

        mIvUserProfile = findViewById(R.id.iv_user_profile)
        mRlEditProfile = findViewById(R.id.rl_edit_profile)
        mEtName = findViewById(R.id.et_name)
        mEtEmail = findViewById(R.id.et_email)
        mEtPhone = findViewById(R.id.et_phone)
        mTvChangePassword = findViewById(R.id.tv_change_password)
        mBtnSave = findViewById(R.id.btn_save)

        mAppPreferences = AppPreferences.getInstance(this)
        mPermissionHelper = PermissionHelper(this, this)

        mEtEmail.isFocusable = false
        mEtEmail.isEnabled = false

        permissions = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        mPermissionHelper.requestPermission(
            permissions,
            "Storage permission required for uploading profile picture"
        )
    }

    private fun showChangePasswordDialog() {
        changePasswordDialog = Dialog(this)
        changePasswordDialog.setContentView(R.layout.dialog_change_password)
        changePasswordDialog.setCancelable(true)
        val oldPassword: EditText = changePasswordDialog.findViewById(R.id.et_old_password)
        val newPassword: EditText = changePasswordDialog.findViewById(R.id.et_new_password)
        val confirmPassword: EditText = changePasswordDialog.findViewById(R.id.et_confirm_password)
        val btnSave: Button = changePasswordDialog.findViewById(R.id.btn_save)
        changePasswordDialog.show()


        btnSave.setOnClickListener {
            if (oldPassword.text.toString().trim() == "") {
                ToastUtil.showErrorToast(this, "Enter old password.")
            } else if (newPassword.text.toString().trim() == "") {
                ToastUtil.showErrorToast(this, "Enter new password.")
            } else if (confirmPassword.text.toString().trim() != newPassword.text.toString()
                    .trim()
            ) {
                ToastUtil.showErrorToast(this, "Password does not match.")
            } else if (oldPassword.text.toString().trim() == newPassword.text.toString().trim()) {
                ToastUtil.showErrorToast(this, "Same as old password.")
            } else {
                showLoader(this, "Saving...")
                ApiResponse().hitVolleyApi(
                    this,
                    this,
                    getParams(Constants.CHANGE_PASSWORD),
                    Constants.CHANGE_PASSWORD
                )
            }
        }

        val window = changePasswordDialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1001 && grantResults[0] == 0 && permissions.contentEquals(arrayOf(android.Manifest.permission.CAMERA))) {
            /*CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)*/
        } else if (requestCode == 1002 && grantResults[0] == 0 && permissions.contentEquals(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        ) {
           /* CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)*/
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        super.onActivityResult(requestCode, resultCode, result)

        /*if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && result != null) {
            val data: CropImage.ActivityResult =
                CropImage.getActivityResult(result)
            if (resultCode == Activity.RESULT_OK) {
                try {
                    val resultUri: Uri? = data.uri
                    imagePath = resultUri.toString()
                    photo = File(URI("file:///" + data.uri.path!!.replace(" ", "%20")))
                    Glide.with(this)
                        .load(imagePath)
                        .thumbnail(0.1f)
                        .apply(RequestOptions.circleCropTransform())
                        .into(mIvUserProfile)
                } catch (exception: Exception) {
                    exception.stackTrace
                    Log.e("Image error", exception.message.toString())
                    dismissLoader()
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error: java.lang.Exception? = data.error
            }
        }*/
    }

    private fun getParams(type: String): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        if (type == Constants.UPDATE_PROFILE) {
            hashMap["user_fullname"] = mEtName.text.toString().trim()
            hashMap["user_mob"] = mEtPhone.text.toString().trim()
            hashMap["user_id"] = mAppPreferences.getUserId()
        } else if (type == Constants.CHANGE_PASSWORD) {
            hashMap["user_id"] = mAppPreferences.getUserId()
            hashMap["old_password"] =
                (changePasswordDialog.findViewById(R.id.et_old_password) as EditText).text.toString()
                    .trim()
            hashMap["password"] =
                (changePasswordDialog.findViewById(R.id.et_new_password) as EditText).text.toString()
                    .trim()
        }
        return hashMap
    }

    override fun onResponse(response: String, type: String) {
        if (type == Constants.CHANGE_PASSWORD) {
            try {
                val jsonObject = JSONObject(response)
                if (jsonObject.getString("status") == "true") {
                    ToastUtil.showSuccessToast(this, jsonObject.getString("message"))
                    changePasswordDialog.dismiss()
                } else {
                    ToastUtil.showErrorToast(this, jsonObject.getString("message"))
                }
            } catch (exception: Exception) {
                exception.stackTrace
                Log.e(Constants.CHANGE_PASSWORD, exception.message.toString())
            } finally {
                dismissLoader()
            }
        }else if(type == Constants.UPDATE_PROFILE){
            dismissLoader()
            try {
                val jsonObject = JSONObject(response.toString())
                if (jsonObject.getString("status") == "true") {
                    val mainObject = jsonObject.getJSONObject("data")
                    mAppPreferences.setUserMobile(mainObject.getString("user_mob"))
                    mAppPreferences.setUserName(mainObject.getString("user_fullname"))
                    if (mAppPreferences.getUserImage() == "")
                        mAppPreferences.setUserImage(mainObject.getString("user_img"))
                    else {
                        if (mainObject.getString("user_img") != "") {
                            mAppPreferences.setUserImage(mainObject.getString("user_img"))
                        }
                    }

                    ToastUtil.showSuccessToast(
                        this@EditProfileActivity,
                        "Profile updated successfully."
                    )
                    HomeActivity.open(this@EditProfileActivity)
                    finish()
                } else {
                    ToastUtil.showErrorToast(
                        this@EditProfileActivity,
                        jsonObject.getString("message")
                    )
                }
            } catch (e: java.lang.Exception) {
                e.message
                e.printStackTrace()
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
