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
package net.cosmiclion.opms.main.notices.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.cosmiclion.opms.login.model.UserInfoData;
import net.cosmiclion.opms.login.model.UserInfoDomain;
import net.cosmiclion.opms.login.model.UserInfoResponse;
import net.cosmiclion.opms.utils.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class used to transform {@link UserInfoResponse} (in the data layer) to {@link UserInfoData} in the
 * domain layer.
 */

public class NoticeMapper {

    /**
     * Transform a {@link UserInfoResponse} into an {@link UserInfoDomain}.
     *
     * @param jsonData Object to be transformed.
     * @return {@link UserInfoDomain} if valid {@link UserInfoResponse} otherwise null.
     */
    public static NoticeDomain transform(String jsonData) {
        NoticeDomain domain = new NoticeDomain();
        if (jsonData != null) {
            NoticeData data = new Gson().fromJson(jsonData, NoticeData.class);
            domain.board_id = data.board_id;
            domain.board_type = data.board_type;
            domain.board_category = data.board_category;
            domain.member_id = data.member_id;

            domain.board_title = data.board_title;
            domain.board_content = data.board_content;
            domain.board_read_count = data.board_read_count;
            domain.board_date = data.board_date;
        }
        return domain;
    }

    public static NoticeDomain transform(NoticeData data) {
        NoticeDomain domain = new NoticeDomain();
        if (data != null) {
            domain.board_id = data.board_id;
            domain.board_type = data.board_type;
            domain.board_category = data.board_category;
            domain.member_id = data.member_id;

            domain.board_title = data.board_title;
            domain.board_content = data.board_content;
            domain.board_read_count = data.board_read_count;
            domain.board_date = data.board_date;
        }
        return domain;
    }

    public static List<NoticeDomain> transformList(String jsonDomain) {
        List<NoticeDomain> booksDomain = new ArrayList<NoticeDomain>();
        List<NoticeData> booksData = new GsonBuilder().setPrettyPrinting().create()
                .fromJson(jsonDomain, new TypeToken<List<NoticeData>>() {
                }.getType());
        NoticeDomain itemDomain;
        Debug.i("TAG_TAG", "==========hhhhhhhhh");
        Debug.i("TAG_TAG", "board_title=" + booksData.get(0).board_title +
                " - board_category=" + booksData.get(0).board_category);
        for (NoticeData item : booksData) {
            itemDomain = transform(item);
            if (itemDomain != null) {
                booksDomain.add(itemDomain);
            }
        }
        return booksDomain;
    }
}
