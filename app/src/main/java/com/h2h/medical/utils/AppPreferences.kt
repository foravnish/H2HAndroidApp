package com.h2h.medical.utils

import android.content.Context
import android.content.SharedPreferences
import com.h2h.medical.R

class AppPreferences {

    companion object {

        private var mInstance: AppPreferences? = null
        private var mPreferences: SharedPreferences? = null
        private var mEditor: SharedPreferences.Editor? = null

        fun getInstance(context: Context): AppPreferences {
            if (mInstance == null) {
                mInstance = AppPreferences()
            }
            if (mPreferences == null) {
                mPreferences = context.getSharedPreferences(
                    context.getString(R.string.app_name),
                    Context.MODE_PRIVATE
                )
                mEditor = mPreferences!!.edit()
            }
            return mInstance as AppPreferences
        }

        const val PREFERENCE_USER_ID: String = "user_id"
        const val PREFERENCE_TOKEN_ID: String = "token_id"
        const val PREFERENCE_USER_EMAIL = "user_email"
        const val PREFERENCE_USER_MOBILE = "user_mobile"
        const val PREFERENCE_USER_IMAGE = "user_image"
        const val PREFERENCE_USER_NAME = "user_name"
        const val PREFERENCE_USER_STATUS = "user_status"
        const val PREFERENCE_LOGIN_STATUS = "login_status"
        const val PREFERENCE_TIME_ZONE = "daily_flow_time_zone"
       // const val PREFERENCE_DAILY_FLOW_FORM = "daily_flow"
        const val PREFERENCE_WEEKLY_SHEET_DATA = "weekly_sheet_data"
        const val PREFERENCE_WEEKLY_SHEET_FORM_DATA = "weekly_sheet_form_data"

    }

    fun setUserId(value: String) {
        mEditor!!.putString(PREFERENCE_USER_ID, value)
        mEditor!!.commit()
    }

    fun getUserId(): String {
        return mPreferences?.getString(PREFERENCE_USER_ID, "")!!
    }

    fun setUserEmail(value: String) {
        mEditor!!.putString(PREFERENCE_USER_EMAIL, value)
        mEditor!!.commit()
    }

    fun getUserEmail(): String {
        return mPreferences?.getString(PREFERENCE_USER_EMAIL, "")!!
    }

    fun setUserName(value: String) {
        mEditor!!.putString(PREFERENCE_USER_NAME, value)
        mEditor!!.commit()
    }

    fun getUserName(): String {
        return mPreferences?.getString(PREFERENCE_USER_NAME, "")!!
    }

    fun setUserMobile(value: String) {
        mEditor!!.putString(PREFERENCE_USER_MOBILE, value)
        mEditor!!.commit()
    }

    fun getUserMobile(): String {
        return mPreferences?.getString(PREFERENCE_USER_MOBILE, "")!!
    }

    fun setUserImage(value: String) {
        mEditor!!.putString(PREFERENCE_USER_IMAGE, value)
        mEditor!!.commit()
    }

    fun getUserImage(): String {
        return mPreferences?.getString(PREFERENCE_USER_IMAGE, "")!!
    }


    fun setUserPDF(value: HashSet<String>) {
        mEditor!!.putStringSet("PDf", value)
        mEditor!!.commit()
    }

    fun getUserIPDF(): Set<String> {
        return mPreferences?.getStringSet("PDf",null)!!
    }


    fun setUserStatus(value: String) {
        mEditor!!.putString(PREFERENCE_USER_STATUS, value)
        mEditor!!.commit()
    }

    fun getUserStatus(): String {
        return mPreferences?.getString(PREFERENCE_USER_STATUS, "")!!
    }

    fun setLogin(value: Boolean) {
        mEditor!!.putBoolean(PREFERENCE_LOGIN_STATUS, value)
        mEditor!!.commit()
    }

    fun getLogin(): Boolean {
        return mPreferences!!.getBoolean(PREFERENCE_LOGIN_STATUS, false)
    }

    fun setTimeZone(value: String) {
        mEditor!!.putString(PREFERENCE_TIME_ZONE, value)
        mEditor!!.commit()
    }

    fun getTimeZone(): String {
        return mPreferences!!.getString(PREFERENCE_TIME_ZONE, "")!!
    }

    fun setFirstTimeZoneData(value: String,key: String) {
        mEditor!!.putString(key, value)
        mEditor!!.commit()
    }

    fun getFirstTimeZoneData(key:String): String {
        return mPreferences!!.getString(key, "")!!
    }








    fun setDailyFlowForm(value: String,key:String) {
        mEditor!!.putString(key, value)
        mEditor!!.commit()
    }

    fun getDailyFlowForm(key:String): String {
        return mPreferences!!.getString(key, "")!!
    }

    fun resetTimeZone() {
        val value = "06:00 – 07:59@07:00 – 09:59@10:00 – 11:59@12:00 - 13:59@14:00 - 15:59"
        mEditor!!.putString(PREFERENCE_TIME_ZONE, value)
        mEditor!!.commit()
    }

    fun resetDailyFomData( value:Int) {
        mEditor!!.putString("daily_flow_first_time_zone_data${value}0", "")
        mEditor!!.putString("daily_flow_first_time_zone_data${value}1", "")
        mEditor!!.putString("daily_flow_first_time_zone_data${value}2", "")
        mEditor!!.putString("daily_flow_first_time_zone_data${value}3", "")
        mEditor!!.putString("daily_flow_first_time_zone_data${value}4", "")
        mEditor!!.putString("daily_flow$value", "")
    }

    fun setWeeklySheetData(value: String) {
        mEditor!!.putString(PREFERENCE_WEEKLY_SHEET_DATA, value)
        mEditor!!.commit()
    }

    fun getWeeklySheetData(): String {
        return mPreferences!!.getString(PREFERENCE_WEEKLY_SHEET_DATA, "")!!
    }

    fun setWeeklySheetFormData(value: String) {
        mEditor!!.putString(PREFERENCE_WEEKLY_SHEET_FORM_DATA, value)
        mEditor!!.commit()
    }

    fun getWeeklySheetFormData(): String {
        return mPreferences!!.getString(PREFERENCE_WEEKLY_SHEET_FORM_DATA, "")!!
    }

    fun getTokenIdForFirebase(): String {
        return mPreferences?.getString(PREFERENCE_TOKEN_ID, "")!!
    }

    fun setTokenIdForFirebase(token_id: String) {
        mEditor!!.putString(PREFERENCE_TOKEN_ID, token_id)
        mEditor!!.commit()
    }

    fun clearPreference() {
        mEditor!!.clear()
        mEditor!!.commit()
    }
}