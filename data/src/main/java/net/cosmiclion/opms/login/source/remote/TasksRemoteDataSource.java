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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import net.cosmiclion.opms.login.TasksDataSource;
import net.cosmiclion.opms.login.model.BaseValueResponse;
import net.cosmiclion.opms.login.model.LoginRequest;
import net.cosmiclion.opms.login.model.LoginResponse;
import net.cosmiclion.opms.utils.AES256Cipher;
import net.cosmiclion.opms.utils.Constants;
import net.cosmiclion.opms.utils.Debug;
import net.cosmiclion.opms.volley.ApiServices;
import net.cosmiclion.opms.volley.GsonRequest;
import net.cosmiclion.opms.volley.VolleyManager;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class TasksRemoteDataSource implements TasksDataSource {
    private final String TAG = getClass().getSimpleName();
    private static TasksRemoteDataSource INSTANCE = null;
    private VolleyManager mVolleyManager = null;

    private static final int BASE_VALUE = 9999;

    public static TasksRemoteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRemoteDataSource(context);
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private TasksRemoteDataSource(@NonNull Context context) {
        checkNotNull(context);
        if (mVolleyManager == null) {
            mVolleyManager = VolleyManager.getInstance(context);
        }
    }

    @Override
    public void getBaseValueResponse(@NonNull final LoadBaseValueCallback callback) {
        Debug.i(TAG, "========getBaseValueResponse=======");
        try {
            GsonRequest<BaseValueResponse> gsonRequest = new GsonRequest<BaseValueResponse>(Request.Method.POST, ApiServices.BASE_VALUE, null,
                    new Response.Listener<BaseValueResponse>() {
                        @Override
                        public void onResponse(BaseValueResponse response) {
                            try {
                                Debug.i(TAG, "BaseValue response =" + response.getBaseValue());
                                int baseValue = Integer.parseInt(AES256Cipher.AES_Decode(response.getBaseValue()));
                                Debug.i(TAG, "BaseValue response before SUB=" + baseValue);
                                baseValue = baseValue - BASE_VALUE;
                                Debug.i(TAG, "BaseValue response after sub=" + baseValue);
                                String baseValueEncrypted = AES256Cipher.AES_Encode(baseValue + "");

                                Debug.i(TAG, "baseValueEncrypted=" + baseValueEncrypted);
                                callback.onBaseValueLoaded(new BaseValueResponse(baseValueEncrypted));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            callback.onDataNotAvailable(error.getMessage());
                            error.printStackTrace();
                            Debug.i(TAG, "VolleyError error =" + error.getLocalizedMessage());
                        }
                    });
            gsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                    20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mVolleyManager.addToRequestQueue(gsonRequest);
        } catch (Exception e) {
            Debug.i(TAG, "Exception =" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void getLoginResponse(@NonNull LoginRequest loginRequest, @NonNull final LoadLoginCallback callback) {
        Debug.i(TAG, "========getLoginResponse=======");
        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put(Constants.PARAMS_ID, loginRequest.getEmailAddress());
            params.put(Constants.PARAMS_PWD, loginRequest.getPassword());
            params.put(Constants.PARAMS_BASEVALUE, loginRequest.getBaseValue());

            GsonRequest<LoginResponse> gsonRequest = new GsonRequest<LoginResponse>(Request.Method.POST, ApiServices.LOGIN_SERVICE,
                    params,
                    new Response.Listener<LoginResponse>() {
                        @Override
                        public void onResponse(LoginResponse response) {
                            callback.onLoginLoaded(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            callback.onDataNotAvailable(error.getMessage());
                        }
                    });
            mVolleyManager.addToRequestQueue(gsonRequest);
        } catch (Exception e) {
            Debug.i(TAG, "Exception =" + e.getMessage());
            e.printStackTrace();
        }
    }

}
