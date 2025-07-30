package com.h2h.medical.Volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class UpdateListener<T> implements Listener<String>, ErrorListener {

    private int reqType;
    private onUpdateViewListener onUpdateViewListener;
    private Context mContext;
    private Class<T> classObject;

    public UpdateListener(Context context, onUpdateViewListener onUpdateView, int reqType, Class<T> classObject) {
        this.reqType = reqType;
        this.onUpdateViewListener = onUpdateView;
        mContext = context;
        this.classObject = classObject;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (onUpdateViewListener == null) {
            return;
        }
        onUpdateViewListener.updateView(VolleyExceptionUtil.getErrorMessage(error), false, reqType);
    }

    @Override
    public void onResponse(final String responseStr) {
        if (BuildConfig.DEBUG) {
            Log.i(VolleyStringRequest.mNetworkTag, responseStr);
        }
        if (mContext == null || onUpdateViewListener == null) {
            return;
        }
//		try {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					final Object responseObject = new Gson().fromJson(responseStr, classObject);
//					if (mContext instanceof BaseActivity) {
//						final Activity mActivity = (Activity) mContext;
//						mActivity.runOnUiThread(new Runnable() {
//							@SuppressLint("InlinedApi")
//							@Override
//							public void run() {
//
//
//
//							}
//						});
//					} else {
//						onUpdateViewListener.updateView(responseObject, true, reqType);
//					}
//
//				}
//			}).start();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			onUpdateViewListener.updateView(responseStr, false, reqType);
//		}
//
    }

}
