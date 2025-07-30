package com.h2h.medical.Volley;

import com.android.volley.TimeoutError;

import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class VolleyExceptionUtil {


	/**
	 * @param pException
	 * @return
	 */
	public static String getErrorMessage(Exception pException) {
//		if (BuildConfig.DEBUG) {
//			Log.e(LOG_TAG, "getErrorMessage() " + pException);
//		}
		if (pException instanceof URISyntaxException) {
			return "Request URL is not valid.";
		}
		if (pException instanceof UnknownHostException) {
			return "No host server found for requested URL.";
		}
		if (pException instanceof SocketException || pException instanceof TimeoutError) {
			return "Request timed-out while waiting for server response.";
		}
		if (pException instanceof IOException) {
			return "Some error occured while connecting to server. Please try again.";
		}
		return "Could not connect to server. Please try again.";
	}
}
