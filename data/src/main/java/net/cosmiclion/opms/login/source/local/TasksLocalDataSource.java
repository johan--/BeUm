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

package net.cosmiclion.opms.login.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.TasksDataSource;
import net.cosmiclion.opms.login.model.LoginRequest;
import net.cosmiclion.opms.login.model.UserInfoData;
import net.cosmiclion.opms.utils.Debug;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class TasksLocalDataSource implements TasksDataSource {

    private final String TAG = getClass().getSimpleName();
    private static TasksLocalDataSource INSTANCE;

    private TasksDbHelper mDbHelper;
    private SQLiteDatabase mDb;

    // Prevent direct instantiation.
    private TasksLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new TasksDbHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }

    public static TasksLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TasksLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getLoginResponse(@NonNull LoginRequest loginRequest, @NonNull LoadLoginCallback callback) {

    }

    @Override
    public void getUserInfoResponse(@NonNull String token, @NonNull LoadUserInfoCallback callback) {

    }

    @Override
    public void saveUserInfo(@NonNull UserInfoData userInfoData) {
        try {
            checkNotNull(userInfoData);

            ContentValues values = new ContentValues();
            values.put(UserInfoContract.Entry.COLUMN_NAME_MEMBER_ID, userInfoData.member_id);
            values.put(UserInfoContract.Entry.COLUMN_NAME_MEMBER_ACCOUNT, userInfoData.member_account);
            values.put(UserInfoContract.Entry.COLUMN_NAME_MEMBER_TRUENAME, userInfoData.member_truename);
            values.put(UserInfoContract.Entry.COLUMN_NAME_MEMBER_SEX, userInfoData.member_sex);

            values.put(UserInfoContract.Entry.COLUMN_NAME_MEMBER_BIRTHDAY, userInfoData.member_birthday);
            values.put(UserInfoContract.Entry.COLUMN_NAME_MEMBER_MOBILE, userInfoData.member_mobilephone);
            values.put(UserInfoContract.Entry.COLUMN_NAME_ALLOW_EMAIL, userInfoData.allow_email_inform);
            values.put(UserInfoContract.Entry.COLUMN_NAME_ALLOW_SMS, userInfoData.allow_sms_inform);

            values.put(UserInfoContract.Entry.COLUMN_NAME_ALLOW_APP, userInfoData.allow_app_inform);
            values.put(UserInfoContract.Entry.COLUMN_NAME_MEMBER_ADDTIME, userInfoData.member_addtime);
            values.put(UserInfoContract.Entry.COLUMN_NAME_LOGIN_TIME, userInfoData.member_login_time);
            values.put(UserInfoContract.Entry.COLUMN_NAME_OLD_LOGIN_TIME, userInfoData.member_old_login_time);

            values.put(UserInfoContract.Entry.COLUMN_NAME_ADULT_CHECK, userInfoData.adult_check);
            values.put(UserInfoContract.Entry.COLUMN_NAME_LOGIN_NUM, userInfoData.member_login_num);
            values.put(UserInfoContract.Entry.COLUMN_NAME_LOGIN_IP, userInfoData.member_login_ip);
            values.put(UserInfoContract.Entry.COLUMN_NAME_OLD_LOGIN_IP, userInfoData.member_old_login_ip);

            values.put(UserInfoContract.Entry.COLUMN_NAME_MEMBER_STATE, userInfoData.member_state);
            values.put(UserInfoContract.Entry.COLUMN_NAME_MOBILE_TOKEN, userInfoData.member_mobile_token);

            mDb.insert(UserInfoContract.Entry.TABLE_NAME, null, values);
            Debug.i(TAG, "insert success");
        } catch (IllegalStateException e) {
            // Send to analytics, log etc
        }
    }

    @Override
    public void getBaseImageUrlResponse(@NonNull LoadBaseImageUrlCallback callback) {

    }
}
