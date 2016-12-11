package net.cosmiclion.opms.main.purchase.service;

import net.cosmiclion.opms.login.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by longpham on 12/10/2016.
 */

public interface PurchaseService {
    @GET("api/purchases")
    Call<ResponseData> getBookPurchaseInfo(@Header("Authorization") String token);

    @GET("api/settings")
    Call<ResponseData> getBaseImageUrl();
}
