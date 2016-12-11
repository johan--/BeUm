package net.cosmiclion.opms.network.retrofit2;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by longpham on 12/10/2016.
 */

public class CachingControlInterceptor implements Interceptor {
    private Context mContext;

    public CachingControlInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // Add Cache Control only for GET methods
        if (request.method().equals("GET")) {
            if (Connectivity.isConnected(mContext)) {
                // 1 day
                request = request.newBuilder()
//                        .header("Cache-Control", "only-if-cached")
                        .header("Cache-Control", "public, max-stale=60")
                        .build();
            } else {
                // 4 weeks stale
                request = request.newBuilder()
                        .header("Cache-Control", "public, max-stale=2419200")
                        .build();
            }
        }

        Response originalResponse = chain.proceed(request);
        return originalResponse.newBuilder()
                .header("Cache-Control", "max-age=600")
                .build();
    }
}