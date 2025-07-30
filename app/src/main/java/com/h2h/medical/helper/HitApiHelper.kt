package com.h2h.medical.helper

import com.android.volley.VolleyError
import org.json.JSONException

interface HitApiHelper {

    @Throws(JSONException::class)
    fun onResponse(response: String, type: String)

    fun onError(e: VolleyError)
}