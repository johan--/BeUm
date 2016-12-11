package net.cosmiclion.opms.login.service;

import net.cosmiclion.opms.login.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by longpham on 12/9/2016.
 */

public interface UserInfoService {

    @GET("api/users/me")
    Call<ResponseData> getUserInfo(@Header("Authorization") String token);
}
