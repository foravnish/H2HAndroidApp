package com.h2h.medical.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.h2h.medical.R
import com.h2h.medical.activities.*
import com.h2h.medical.utils.AppPreferences
import com.h2h.medical.utils.AppUtils
import com.h2h.medical.utils.Constants
import kotlinx.android.synthetic.main.my_account_fragment.ll_chek_list

class MyAccountFragment : Fragment() {

    private lateinit var v: View
    private lateinit var mContext: Context
    private lateinit var myActivity: Activity

    private lateinit var mLlEditProfile: LinearLayout
    private lateinit var mLlPrivacyPolicy: LinearLayout
    private lateinit var mLlLogout: LinearLayout
    private lateinit var mLlContactUs: LinearLayout
    private lateinit var mLlViewPDF: LinearLayout
    private lateinit var mLlTerms: LinearLayout
    private lateinit var ll_chek_list: LinearLayout
    private lateinit var mIvUserProfile: ImageView
    private lateinit var mTvUserName: TextView
    private lateinit var txtVersionName: TextView



    companion object {
        fun newInstance(): MyAccountFragment {
            return MyAccountFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.my_account_fragment, container, false)


        initViews()
        listeners()

        return v
    }


    private fun listeners() {

        mLlLogout.setOnClickListener {
            AlertDialog.Builder(mContext)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("")
                .setCancelable(false)
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton(
                    "Yes"
                ) { _, _ ->
                    AppPreferences.getInstance(mContext).clearPreference()
                    SplashActivity.open(myActivity)
                    myActivity.finish()
                }
                .setNegativeButton(
                    "N/A"
                ) { _, _ -> }
                .show()
        }


        mLlEditProfile.setOnClickListener {
            EditProfileActivity.open(myActivity)
        }

        mLlContactUs.setOnClickListener {
            ContactUsActivity.open(myActivity)
        }

        mLlViewPDF.setOnClickListener {
            PDFActivity.open(myActivity)
        }

        mLlPrivacyPolicy.setOnClickListener {
            WebViewActivity.open(myActivity, "privacyPolicy")
        }

        mLlTerms.setOnClickListener {
            WebViewActivity.open(myActivity, "terms")
        }
        ll_chek_list.setOnClickListener {
            val intent = Intent(myActivity, HartSkillCheckListActivity::class.java)
            myActivity.startActivity(intent)
        }
    }

    private fun initViews() {
        mContext = requireContext()
        myActivity = this.activity!!

        mLlEditProfile = v.findViewById(R.id.ll_edit_profile)
        mLlPrivacyPolicy = v.findViewById(R.id.ll_privacy_policy)
        mLlLogout = v.findViewById(R.id.ll_logout)
        mLlContactUs = v.findViewById(R.id.ll_contact_us)
        mLlViewPDF = v.findViewById(R.id.ll_pdf)
        mLlTerms = v.findViewById(R.id.ll_terms)
        ll_chek_list= v.findViewById(R.id.ll_chek_list)
        mIvUserProfile = v.findViewById(R.id.iv_user_profile)
        mTvUserName = v.findViewById(R.id.tv_user_name)
        txtVersionName = v.findViewById(R.id.txtVersionName)
        txtVersionName.text="v"+AppUtils.getAppVersion()

        mTvUserName.text = AppPreferences.getInstance(myActivity).getUserName()
        if (AppPreferences.getInstance(myActivity).getUserImage() != "") {
            Glide.with(mContext)
                .load(Constants.IMAGE_URL + AppPreferences.getInstance(mContext).getUserImage())
                .apply(RequestOptions().circleCrop())
                .into(mIvUserProfile)
        } else {
            Glide.with(mContext)
                .load(R.drawable.ic_profile)
                .apply(RequestOptions().circleCrop())
                .into(mIvUserProfile)
        }
    }

}