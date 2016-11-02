/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
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
package net.cosmiclion.opms.login.model;

/**
 * Mapper class used to transform {@link BaseValueResponse} (in the data layer) to {@link BaseValue} in the
 * domain layer.
 */

public class BaseValueResponseMapper {

  /**
   * Transform a {@link BaseValueResponse} into an {@link BaseValue}.
   *
   * @param baseValueResponse Object to be transformed.
   * @return {@link BaseValue} if valid {@link BaseValueResponse} otherwise null.
   */
  public static BaseValue transform(BaseValueResponse baseValueResponse) {
    BaseValue baseValue = null;
    if (baseValueResponse != null) {
      baseValue = new BaseValue();
      baseValue.setBasevalue(baseValueResponse.getBaseValue());
    }
    return baseValue;
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
