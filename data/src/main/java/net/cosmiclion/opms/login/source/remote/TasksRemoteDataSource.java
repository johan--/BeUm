/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.cosmiclion.opms.login.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.TasksDataSource;
import net.cosmiclion.opms.login.model.LoginRequest;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.login.model.UserInfoData;
import net.cosmiclion.opms.login.service.LoginService;
import net.cosmiclion.opms.login.service.UserInfoService;
import net.cosmiclion.opms.main.purchase.service.PurchaseService;
import net.cosmiclion.opms.network.retrofit2.ApiClient;
import net.cosmiclion.opms.utils.Debug;

import retrofit2.Call;
import retrofit2.Callback;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class TasksRemoteDataSource implements TasksDataSource {
    private final String TAG = getClass().getSimpleName();
    private static TasksRemoteDataSource INSTANCE = null;
    private Context mContext;

    public static TasksRemoteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRemoteDataSource(context);
        }
        return INSTANCE;
    }

    private TasksRemoteDataSource(@NonNull Context context) {
        this.mContext = checkNotNull(context);
    }

    @Override
    public void getLoginResponse(@NonNull LoginRequest loginRequest, @NonNull final LoadLoginCallback callback) {
        Debug.i(TAG, "===getLoginResponse===");
        Debug.i(TAG, loginRequest.getEmailAddress() + " ========Pass_encode======= " + loginRequest.getPassword());

//        TypeAdapterFactory adap = new MyTypeAdapterFactory();
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapterFactory(adap)
//                .create();
//        String resp = "{ \"response\": { \"member_mobile_token\": \"1czcv\" }, \"success\": true }";
//        String json = gson.toJson
//        Debug.i(TAG, "JSON="+ json);

        LoginService loginService = ApiClient.getClient(mContext).create(LoginService.class);

        final Call<ResponseData> call = loginService.doLogin(
                loginRequest.getEmailAddress(), loginRequest.getPassword());

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, retrofit2.Response<ResponseData> response) {
//                Debug.i(TAG, "===getLoginResponse===" + ((MobileToken)response.body().getResponse()).getMobileToken());
                callback.onLoginLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Debug.i(TAG, "Something went wrong: " + t.getMessage());
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getUserInfoResponse(@NonNull String token, @NonNull final LoadUserInfoCallback callback) {
        Debug.i(TAG, "===getUserInfoResponse===");
        UserInfoService userInfoService = ApiClient.getClient(mContext).create(UserInfoService.class);

        final Call<ResponseData> call = userInfoService.getUserInfo(token);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call,
                                   retrofit2.Response<ResponseData> response) {

                callback.onUserInfoLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Debug.i(TAG, "Something went wrong: " + t.getMessage());
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void saveUserInfo(@NonNull UserInfoData userInfoData) {

    }

    @Override
    public void getBaseImageUrlResponse(@NonNull final LoadBaseImageUrlCallback callback) {
        PurchaseService purchaseService = ApiClient.getClient(mContext).create(PurchaseService.class);

        final Call<ResponseData> call = purchaseService.getBaseImageUrl();

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call,
                                   retrofit2.Response<ResponseData> response) {
                callback.onBaseImageUrlLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Debug.i(TAG, "Something went wrong: " + t.getMessage());
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
