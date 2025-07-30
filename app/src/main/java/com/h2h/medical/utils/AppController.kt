package com.h2h.medical.utils

import android.annotation.SuppressLint
import android.os.StrictMode
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class AppController : Application() {

    private lateinit var mInstance: AppController
//    private var mRequestQueue: RequestQueue? = null


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        printHashKey()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }


    override fun onTerminate() {
        super.onTerminate()
    }


    override fun onCreate() {
        super.onCreate()
        mInstance = this
        context=this
    }

    companion object {

        val TAG = AppController::class.java
            .simpleName
        @SuppressLint("StaticFieldLeak")
        @get:Synchronized
        var mInstance: AppController? = null
        var context: Context? = null
    }

    fun printHashKey() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        for (signature in info.signatures!!) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i(TAG, "Hash_Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "printHashKey()", e)
        } catch (e: Exception) {
            Log.e(TAG, "printHashKey()", e)
        }

    }
}
