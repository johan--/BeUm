package net.cosmiclion.opms.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longpham on 12/9/2016.
 */

public class Retrofit2Response {
    @SerializedName("response")
    private Object response;

    @SerializedName("success")
    private boolean success;

    public Retrofit2Response() {
    }

    public Retrofit2Response(String response) {
        this.response = response;
    }

    public Object getResponse() {
        return response;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
