package com.h2h.medical.utils

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.h2h.medical.helper.HitApiHelper
import com.h2h.medical.utils.Constants.BASE_URL

class ApiResponse {

    fun ApiResponse() {}

    fun hitVolleyApi(
        context: Context,
        hitApiHelper: HitApiHelper,
        params: HashMap<String, String>,
        url: String
    ) {
        val sr =
            object : StringRequest(
                Request.Method.POST, BASE_URL + url,
                Response.Listener<String> { response ->
                    hitApiHelper.onResponse(
                        response,
                        url
                    )
                    AppUtils.printLog(url, response)
                }, Response.ErrorListener { error ->
                    error.message
                    hitApiHelper.onError(error)
                    AppUtils.printLog(url, error.message.toString())
                }) {
                override fun getParams(): Map<String, String> {
                    return params
                }
            }

        val queue = Volley.newRequestQueue(context)
        queue.add(sr)
    }
}