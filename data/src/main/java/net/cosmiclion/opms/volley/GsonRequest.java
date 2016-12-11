package net.cosmiclion.opms.volley;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import net.cosmiclion.opms.utils.Debug;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private final Response.Listener<T> listener;
    private final Type mType;
    private final Map<String, String> params;
    private final String mRequestBody;

    /** Charset for request. */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /** Content type for request. */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    public GsonRequest(int method, String url, Map<String, String> params,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        /* Method.GET, Method.POST */
        super(method, url, errorListener);
//        this.mType = type;
        this.mType = new TypeToken<T>() {
        }.getType();
        this.params = params;
        this.listener = listener;
        this.mRequestBody = url;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<String, String>();
        params.put("Accept","application/json");
//        params.put("Content-type","application/json");
        return params;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Debug.i("TAG", "Heeee=");
            Debug.i("TAG", "JSON=" + jsonString);
            T parseObject = gson.fromJson(jsonString, mType);
            return Response.success(parseObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Debug.i("TAG ERR", e.getMessage());
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            Debug.i("TAG ERR", e.getMessage());
            return Response.error(new ParseError(e));
        }
    }

//    @Override
//    public String getBodyContentType() {
//        return PROTOCOL_CONTENT_TYPE;
//    }
//
//    @Override
//    public byte[] getBody() {
//        try {
//            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
//        } catch (UnsupportedEncodingException uee) {
//            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
//                    mRequestBody, PROTOCOL_CHARSET);
//            return null;
//        }
//    }
}