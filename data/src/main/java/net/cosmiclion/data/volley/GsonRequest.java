package net.cosmiclion.data.volley;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import net.cosmiclion.data.utils.Debug;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Response.Listener<T> listener;
    private final Type mType;
    private final Map<String, String> params;

    private static final String PROTOCOL_CHARSET = "utf-8";

    public GsonRequest(int method, String url, Map<String, String> params,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        /* Method.GET, Method.POST */
        super(method, url, errorListener);
//        this.mType = type;
        this.mType = new TypeToken<T>() {
        }.getType();
        this.params = params;
        this.listener = listener;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
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
}