package net.cosmiclion.opms.utils;

import com.android.volley.Request;

/**
 * Created by longpham on 10/23/2016.
 */
public class GsonRequestValue {

    private int method;

    private String url;

    public GsonRequestValue(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
    }

    public static class Builder {

        private int method = Request.Method.GET;

        private String url;

        public Builder(String url) {
            this.url = url;
        }

        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        public GsonRequestValue build() {
            return new GsonRequestValue(this);
        }

    }

}
