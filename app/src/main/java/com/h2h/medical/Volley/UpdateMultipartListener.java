package com.h2h.medical.Volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class UpdateMultipartListener implements Listener, ErrorListener {

	private int	reqType;
	private onUpdateViewListener onUpdateViewListener;
	private Context mContext;

	public UpdateMultipartListener(Context context, onUpdateViewListener onUpdateView, int reqType) {
		this.reqType = reqType;
		this.onUpdateViewListener = onUpdateView;
		mContext = context;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		if (onUpdateViewListener == null) {
			return;
		}
		onUpdateViewListener.updateView(VolleyExceptionUtil.getErrorMessage(error), false, reqType);
	}

	@Override
	public void onResponse(final Object responseStr) {
		if (BuildConfig.DEBUG) {
			Log.i(VolleyStringRequest.mNetworkTag, responseStr.toString());
		}
		if (mContext == null || onUpdateViewListener == null) {
			return;
		}
		try {
			onUpdateViewListener.updateView(responseStr, true, reqType);
		} catch (Exception ex) {
			ex.printStackTrace();
			onUpdateViewListener.updateView(responseStr, false, reqType);
		}
	}

}
