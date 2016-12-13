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

import android.provider.BaseColumns;

import static net.cosmiclion.opms.utils.Constants.COMMA_SEP;
import static net.cosmiclion.opms.utils.Constants.TEXT_TYPE;

/**
 * The contract used for the db to save the tasks locally.
 */
public final class UserInfoContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private UserInfoContract() {}

    /* Inner class that defines the table contents */
    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "userinfo";

        public static final String COLUMN_NAME_MEMBER_ID = "member_id";
        public static final String COLUMN_NAME_MEMBER_ACCOUNT = "member_account";
        public static final String COLUMN_NAME_MEMBER_TRUENAME = "member_truename";
        public static final String COLUMN_NAME_MEMBER_SEX = "member_sex";

        public static final String COLUMN_NAME_MEMBER_BIRTHDAY = "member_birthday";
        public static final String COLUMN_NAME_MEMBER_MOBILE = "member_mobilephone";
        public static final String COLUMN_NAME_ALLOW_EMAIL = "allow_email_inform";
        public static final String COLUMN_NAME_ALLOW_SMS = "allow_sms_inform";

        public static final String COLUMN_NAME_ALLOW_APP = "allow_app_inform";
        public static final String COLUMN_NAME_MEMBER_ADDTIME = "member_addtime";
        public static final String COLUMN_NAME_LOGIN_TIME = "member_login_time";
        public static final String COLUMN_NAME_OLD_LOGIN_TIME = "member_old_login_time";

        public static final String COLUMN_NAME_ADULT_CHECK = "adult_check";
        public static final String COLUMN_NAME_LOGIN_NUM = "member_login_num";
        public static final String COLUMN_NAME_LOGIN_IP = "member_login_ip";
        public static final String COLUMN_NAME_OLD_LOGIN_IP = "member_old_login_ip";

        public static final String COLUMN_NAME_MEMBER_STATE = "member_state";
        public static final String COLUMN_NAME_MOBILE_TOKEN = "member_mobile_token";

        public static final String SQL_CREATE_USER_INFO_TABLE =
                "CREATE TABLE " + UserInfoContract.Entry.TABLE_NAME + " (" +
                        UserInfoContract.Entry.COLUMN_NAME_MEMBER_ID + TEXT_TYPE + " PRIMARY KEY," +
                        UserInfoContract.Entry.COLUMN_NAME_MEMBER_ACCOUNT + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_MEMBER_TRUENAME + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_MEMBER_SEX + TEXT_TYPE + COMMA_SEP +

                        UserInfoContract.Entry.COLUMN_NAME_MEMBER_BIRTHDAY + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_MEMBER_MOBILE + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_ALLOW_EMAIL + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_ALLOW_SMS + TEXT_TYPE + COMMA_SEP +

                        UserInfoContract.Entry.COLUMN_NAME_ALLOW_APP + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_MEMBER_ADDTIME + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_LOGIN_TIME + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_OLD_LOGIN_TIME + TEXT_TYPE + COMMA_SEP +

                        UserInfoContract.Entry.COLUMN_NAME_ADULT_CHECK + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_LOGIN_NUM + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_LOGIN_IP + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_OLD_LOGIN_IP + TEXT_TYPE + COMMA_SEP +

                        UserInfoContract.Entry.COLUMN_NAME_MEMBER_STATE + TEXT_TYPE + COMMA_SEP +
                        UserInfoContract.Entry.COLUMN_NAME_MOBILE_TOKEN + TEXT_TYPE +
                        " )";
    }
}
