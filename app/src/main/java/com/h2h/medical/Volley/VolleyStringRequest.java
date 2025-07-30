package com.h2h.medical.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class VolleyStringRequest extends StringRequest {

    private Map<String, String> mRequestparams;
    public static final String mNetworkTag = "Network";

    private VolleyStringRequest(int method, String url, UpdateListener updateListener, Map<String, String> params) {
        super(method, url, updateListener, updateListener);
        mRequestparams = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mRequestparams;
    }

}
