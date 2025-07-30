package com.h2h.medical.Volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

/**
 * Created by HP on 08-03-2017.
 */

public class VolleyManager {

    private static final String TAG = "VolleyManager";
    private static VolleyManager mVolleyManager;
    private com.android.volley.RequestQueue mRequestQueue;
    private Context mContext;

    private VolleyManager(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    public static VolleyManager getInstance(Context context) {
        if (mVolleyManager == null) {
            mVolleyManager = new VolleyManager(context);
        }
        return mVolleyManager;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, DefaultRetryPolicy.DEFAULT_MAX_RETRIES * 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        mRequestQueue.add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}


