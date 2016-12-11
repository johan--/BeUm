/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.cosmiclion.opms.login.model;

import com.google.gson.Gson;

/**
 * Mapper class used to transform {@link UserInfoResponse} (in the data layer) to {@link UserInfoData} in the
 * domain layer.
 */

public class UserInfoResponseMapper {

    /**
     * Transform a {@link UserInfoResponse} into an {@link UserInfoDomain}.
     *
     * @param jsonUserInfoData Object to be transformed.
     * @return {@link UserInfoDomain} if valid {@link UserInfoResponse} otherwise null.
     */
    public static UserInfoDomain transform(String jsonUserInfoData) {
        UserInfoDomain userInfoDomain = new UserInfoDomain();
        if (jsonUserInfoData != null) {
            UserInfoData userInfoData = new Gson().fromJson(jsonUserInfoData, UserInfoData.class);
            userInfoDomain.member_id = userInfoData.member_id;
            userInfoDomain.member_account = userInfoData.member_account;
            userInfoDomain.member_truename = userInfoData.member_truename;
            userInfoDomain.member_sex = userInfoData.member_sex;
            userInfoDomain.member_birthday = userInfoData.member_birthday;
            userInfoDomain.member_mobilephone = userInfoData.member_mobilephone;
            userInfoDomain.allow_email_inform = userInfoData.allow_email_inform;
            userInfoDomain.allow_sms_inform = userInfoData.allow_sms_inform;
            userInfoDomain.allow_app_inform = userInfoData.allow_app_inform;
            userInfoDomain.member_addtime = userInfoData.member_addtime;
            userInfoDomain.member_login_time = userInfoData.member_login_time;
            userInfoDomain.member_old_login_time = userInfoData.member_old_login_time;
            userInfoDomain.adult_check = userInfoData.adult_check;
            userInfoDomain.member_login_num = userInfoData.member_login_num;
            userInfoDomain.member_login_ip = userInfoData.member_login_ip;
            userInfoDomain.member_old_login_ip = userInfoData.member_old_login_ip;
            userInfoDomain.member_state = userInfoData.member_state;
            userInfoDomain.member_mobile_token = userInfoData.member_mobile_token;
            userInfoDomain.facebook_sync = userInfoData.facebook_sync;
        }
        return userInfoDomain;
    }

//  /**
//   * Transform a List of {@link UserEntity} into a Collection of {@link User}.
//   *
//   * @param userEntityCollection Object Collection to be transformed.
//   * @return {@link User} if valid {@link UserEntity} otherwise null.
//   */
//  public List<User> transform(Collection<UserEntity> userEntityCollection) {
//    List<User> userList = new ArrayList<>(20);
//    User user;
//    for (UserEntity userEntity : userEntityCollection) {
//      user = transform(userEntity);
//      if (user != null) {
//        userList.add(user);
//      }
//    }
//
//    return userList;
//  }
}
