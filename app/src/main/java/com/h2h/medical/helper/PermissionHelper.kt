package com.h2h.medical.helper

import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log

class PermissionHelper : ActivityCompat.OnRequestPermissionsResultCallback {

    private val TAG = PermissionHelper::class.java.simpleName
    private var context: Context
    private val REQUESTCODE = 100
    private val activity: Activity

    constructor(context: Context, activity: Activity) {
        this.context = context
        this.activity = activity
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
    }

    fun requestPermission(permission: Array<String>, messageToExplainPermission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "permission request for = $permission")

            var check = false
            for (i in permission.indices) {
                if (ContextCompat.checkSelfPermission(
                        this.context,
                        permission[i]
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    check = true
                }
            }

            if (check) {
                ActivityCompat.requestPermissions(
                    activity,
                    permission,
                    REQUESTCODE
                )
            }
        }
    }

    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this.context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}