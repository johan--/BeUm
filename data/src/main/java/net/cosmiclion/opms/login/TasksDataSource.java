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

package net.cosmiclion.opms.login;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.model.LoginRequest;
import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.login.model.UserInfoData;


public interface TasksDataSource {

    interface LoadLoginCallback {

        void onLoginLoaded(ResponseData response);

        void onDataNotAvailable(String errorMessage);
    }

    void getLoginResponse(@NonNull LoginRequest loginRequest, @NonNull LoadLoginCallback callback);

    interface LoadUserInfoCallback {

        void onUserInfoLoaded(ResponseData response);

        void onDataNotAvailable(String errorMessage);

    }

    void getUserInfoResponse(@NonNull String token, @NonNull LoadUserInfoCallback callback);

    void saveUserInfo(@NonNull UserInfoData userInfoData);


    interface LoadBaseImageUrlCallback{
        void onBaseImageUrlLoaded(ResponseData response);

        void onDataNotAvailable(String errorMessage);
    }
    void getBaseImageUrlResponse(@NonNull LoadBaseImageUrlCallback callback);
}
