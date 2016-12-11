package net.cosmiclion.opms.main.notices.service;

import net.cosmiclion.opms.login.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by longpham on 12/10/2016.
 */

public interface NoticesService {
    @GET("api/notices")
    Call<ResponseData> getNotices();

}
