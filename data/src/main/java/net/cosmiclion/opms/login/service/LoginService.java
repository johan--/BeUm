package net.cosmiclion.opms.login.service;

import net.cosmiclion.opms.login.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by longpham on 12/7/2016.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseData> doLogin(
            @Field("username") String username,
            @Field("password") String password);
}
