package net.cosmiclion.opms.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by longpham on 12/10/2016.
 */

public class ResultResponse {
    @SerializedName("response")
    Object response  ;

    @SerializedName("success")
    boolean success  ;
}
