package net.cosmiclion.opms.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longpham on 9/18/2016.
 */
public class BaseValueResponse {

    @SerializedName("basevalue")
    private final String basevalue;

    public BaseValueResponse(String baseValue) {
        this.basevalue = baseValue;
    }

    public String getBaseValue() {
        return basevalue;
    }
}
