package net.cosmiclion.opms.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by mini on 2016. 12. 12..
 */

public class Downloader {
    private final String TAG = this.getClass().getSimpleName();
    private final String mDownUrl = "http://malltest.wjopms.com/api/ebook/download";
    private File mDownFile;
    private Context mCtx;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            updateState((String)msg.obj);
        }
    };

    private OnDownloadListener mOnListener;
    public interface OnDownloadListener {
        void onStateChanged(String state);
    }

    public Downloader(Context ctx) {
        mCtx = ctx;
    }

    public void start(String product_id, String filename, OnDownloadListener listener) {
        Log.d(TAG, "product_id:" + product_id + ", filename:" + filename);
        mOnListener = listener;
        mDownFile = new File(mCtx.getFilesDir(), filename);

        FormBody.Builder formbody = new FormBody.Builder();
        formbody.add("product_id", product_id);
        formbody.add("device_key", "3"); // dummy
        formbody.add("device_code", "3"); // for Android fixed.

        Request.Builder requestBuilder = new Request.Builder().url(mDownUrl);
        requestBuilder.addHeader("Authorization", "Basic Mnp4Y3Y6"); // FIXME: 2016. 12. 12. change code.
        requestBuilder.post(formbody.build());

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();

        mHandler.sendMessage(mHandler.obtainMessage(0, "started"));

        client.newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure ");
                mHandler.sendMessage(mHandler.obtainMessage(0, "error"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse ");
                ResponseBody body = response.body();
                long size = body.contentLength();
                BufferedSource source = body.source();
                BufferedSink sink = Okio.buffer(Okio.sink(mDownFile));
                long totalRead = 0;
                long read = 0;
                while ((read = (source.read(sink.buffer(), 2048))) != -1) {
                    sink.emit();
                    totalRead += read;
                    int progress = (int) ((totalRead * 100) / size);
//                    Log.d(TAG, "progress : " + totalRead);

                    mHandler.sendMessage(mHandler.obtainMessage(0, "progress : " + totalRead));
                }
                sink.flush();
                sink.close();
                source.close();
                body.close();

                mHandler.sendMessage(mHandler.obtainMessage(0, "success : " + totalRead));

            }
        });
    }

    /** send download state or error occurred,
     * define state strings as you wish.
     */
    private void updateState(String state) {
        if(mOnListener != null) {
            mOnListener.onStateChanged(state);
        }
    }
}
