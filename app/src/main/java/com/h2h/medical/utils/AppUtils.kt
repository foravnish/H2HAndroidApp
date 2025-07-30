package com.h2h.medical.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import com.h2h.medical.R
import com.h2h.medical.Volley.BuildConfig
import java.text.ParseException
import java.text.SimpleDateFormat

import java.util.*

@SuppressLint("SimpleDateFormat")
object AppUtils {

    val isAppDebug: Boolean = BuildConfig.DEBUG

    val DOB_VALIDATION= 1990
    private val serverUTCDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val serverDefaultDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val serverDefaultDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val serverUTCTimeFormat = SimpleDateFormat("HH:mm:ss")
    private val serverDefaultTimeFormat = SimpleDateFormat("HH:mm:ss")
    private val dayFormat = SimpleDateFormat("EEEE")
    private val targetTimeFormat = SimpleDateFormat("hh:mm a")
    private val calendarDateFormat = SimpleDateFormat("EEE, dd MMM, yyyy")
    private val calendarYearFormat = SimpleDateFormat("MMMM, yyyy")
    private val serverUTCDateFormat = SimpleDateFormat("yyyy-MM-dd")

    init {
        serverUTCDateTimeFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverUTCDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverUTCTimeFormat.timeZone = TimeZone.getTimeZone("UTC")
        serverDefaultDateFormat.timeZone = TimeZone.getDefault()
        serverDefaultDateTimeFormat.timeZone = TimeZone.getDefault()
        serverDefaultTimeFormat.timeZone = TimeZone.getDefault()
        dayFormat.timeZone = TimeZone.getDefault()
        targetTimeFormat.timeZone = TimeZone.getDefault()
        calendarDateFormat.timeZone = TimeZone.getDefault()
        calendarYearFormat.timeZone = TimeZone.getDefault()
    }

    @Throws(ParseException::class)
    fun isValidDate(format: String, pDateString: String?): String {
        var mText = ""
        val date = SimpleDateFormat(format, Locale.US).parse(pDateString)
        when {
            pDateString.equals(getCurrentDate(format)) -> mText = "Same"
            Date().before(date) -> mText = "Future"
            Date().after(date) -> mText = "Past"
        }
        return mText
    }

    @JvmStatic
    fun getCurrentDate(format: String): String {
//        val sdf = SimpleDateFormat("EEE, dd MMMM")
        val sdf = SimpleDateFormat(format, Locale.US)
        val currentDate = sdf.format(Date())
        return currentDate.replace("Sept","Sep")
    }
    fun getAppVersion(): String {
        return BuildConfig.VERSION_NAME
    }

    private var dialog: Dialog? = null

    fun printLog(tag: String, message: String) {
        if (isAppDebug)
            Log.d(tag, message)
    }

    fun isInternetOn(activity: Activity, fragment: Fragment?, requestCode: Int): Boolean {

        var flag = false
        // get Connectivity Manager object to check connection
        val connec = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connec.getNetworkInfo(0)?.state == android.net.NetworkInfo.State.CONNECTED ||
            connec.getNetworkInfo(1)?.state == android.net.NetworkInfo.State.CONNECTING ||
            connec.getNetworkInfo(0)?.state == android.net.NetworkInfo.State.CONNECTING ||
            connec.getNetworkInfo(1)?.state == android.net.NetworkInfo.State.CONNECTED
        ) {

            flag = true

        } else if (connec.getNetworkInfo(0)?.state == android.net.NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(
                1
            )?.state == android.net.NetworkInfo.State.DISCONNECTED
        ) {

            dialogInternet(activity, fragment, requestCode)
            flag = false
        }
        return flag
    }

    private fun dialogInternet(activity: Activity, fragment: Fragment?, requestCode: Int) {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()

        val ad = AlertDialog.Builder(activity)
        ad.setTitle(activity.getString(R.string.noConnection))
        ad.setMessage(activity.getString(R.string.turnOnInternet))
        //        ad.setCancelable(false);
        ad.setNegativeButton(activity.getString(R.string.mobileData)) { dialog, which ->
            val i = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
            if (fragment == null) {
                activity.startActivityForResult(i, requestCode)
            } else {
                fragment.startActivityForResult(i, requestCode)
            }
        }
        ad.setPositiveButton(activity.getString(R.string.Wifi)) { dialog, which ->
            val i = Intent(Settings.ACTION_WIFI_SETTINGS)
            if (fragment == null) {
                activity.startActivityForResult(i, requestCode)
            } else {
                fragment.startActivityForResult(i, requestCode)
            }
        }
        dialog = ad.show()
    }

    @SuppressLint("ServiceCast", "NewApi")
    fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.DONUT) {
            val runningProcesses = am.runningAppProcesses
            try {
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (activeProcess in processInfo.pkgList) {
                            if (activeProcess == context.packageName) {
                                isInBackground = false
                            }
                        }
                    }
                }
            } catch (e: Exception) {
            }

        } else {
            try {
                val taskInfo = am.getRunningTasks(1)
                val componentInfo = taskInfo[0].topActivity
                if (componentInfo?.packageName == context.packageName) {
                    isInBackground = false
                }
            } catch (e: Exception) {
            }

        }
        return isInBackground
    }
}
