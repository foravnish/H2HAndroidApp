package com.h2h.medical.Volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class VolleyMultipartRequest extends Request {

    private static final String TAG = "VolleyMultipartRequest";
    private UpdateMultipartListener updateListener;
    private File file;
    private MultipartEntity mHttpEntity;
    private HashMap<String, String> params;
    private String mFileKey = "photo";
    private Class classz;

    public VolleyMultipartRequest(String url, UpdateMultipartListener updateListener, Class classz, String fileKey, File file, HashMap<String, String> params) {
        super(Method.POST, url, updateListener);
        this.updateListener = updateListener;
        mFileKey = fileKey;
        this.classz = classz;
        this.file = file;
        this.params = params;
        Log.i(TAG, params.toString() + "");
        mHttpEntity = buildMultipartEntity();
    }

	/*
     * private HttpEntity buildMultipartEntity() { MultipartEntityBuilder
	 * builder = MultipartEntityBuilder.create(); String fileName =
	 * file.getName(); builder.addBinaryBody(KEY_PICTURE, file,
	 * ContentType.create("image/jpeg"), fileName); return builder.build(); }
	 */

    private MultipartEntity buildMultipartEntity() {
        mHttpEntity = new MultipartEntity();
        try {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entr : entrySet) {
                mHttpEntity.addPart(entr.getKey(), new StringBody(entr.getValue()));
            }

            if (file != null) {
                mHttpEntity.addPart(mFileKey, new FileBody(file, "image/jpeg"));
            }/*
			 * else { mHttpEntity.addPart(ApiConstants.PARAM_PROFILE_IMAGE, new
			 * StringBody("image")); }
			 */

        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        }

        Log.e(TAG, mHttpEntity.toString());

        return mHttpEntity;
    }

    @Override
    public String getBodyContentType() {
        return mHttpEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        // return Response.success("Uploaded", getCacheEntry());
        String json;
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, String.format("Encoding problem parsing API response. NetworkResponse:%s", response.toString()), e);
            return Response.error(new ParseError(e));
        }
        try {
            return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
//			return Response.success(mGson.fromJson(json, classz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            Log.e(TAG, String.format("Couldn't API parse JSON response. NetworkResponse:%s", response.toString()), e);
            Log.e(TAG, String.format("Couldn't API parse JSON response. Json dump: %s", json));
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        updateListener.onResponse(response);
    }

}
