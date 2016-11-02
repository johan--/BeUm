package net.cosmiclion.opms.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longpham on 9/20/2016.
 */
public class InputStreamRequest extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
    private Map<String, String> mParams;
    //create a static map for directly accessing headers
    public Map<String, String> responseHeaders;

    public InputStreamRequest(int method, String mUrl, HashMap<String, String> params,
                              Response.Listener<byte[]> listener,
                              Response.ErrorListener errorListener) {
        super(method, mUrl, errorListener);
        // this request would never use cache.
        setShouldCache(false);
        mListener = listener;
        mParams = params;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return mParams;
    }

    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;
        //Pass the response data here
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}