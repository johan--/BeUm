package net.cosmiclion.opms.network.retrofit2;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.cosmiclion.data.BuildConfig;
import net.cosmiclion.opms.login.model.ResponseData;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by longpham on 12/7/2016.
 */

public class ApiClient {
    public static final String BASE_URL = "http://malltest.wjopms.com/";
    public static final String BASE_URL_DEBUG = "http://malltest-wjopms-com-hrnjg1izmbul.runscope.net/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            long SIZE_OF_CACHE = 2 * 1024 * 1024; // 2 MiB
            Cache cache = new Cache(new File(context.getCacheDir(), "http"), SIZE_OF_CACHE);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.cache(cache);
            httpClient.networkInterceptors().add(new CachingControlInterceptor(context));

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(logging);

                httpClient.addInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                });
            }


            Gson gson = new GsonBuilder().disableHtmlEscaping()
                    .registerTypeAdapter(ResponseData.class, new ResponseData.Deserializer())
//                    .registerTypeAdapterFactory( new MyTypeAdapterFactory())
//                    .registerTypeAdapter(UserInfoService.class, new UserInfoResponse.UserInfoDeserializer())
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
