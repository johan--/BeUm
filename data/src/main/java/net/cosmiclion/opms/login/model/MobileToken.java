package net.cosmiclion.opms.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longpham on 12/10/2016.
 */

public class MobileToken extends ResultResponse{
    @SerializedName("member_mobile_token")
    private String member_mobile_token;

    public String getMobileToken() {
        return member_mobile_token;
    }

    public void setMobileToken(String member_mobile_token) {
        this.member_mobile_token = member_mobile_token;
    }
}