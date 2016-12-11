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
package net.cosmiclion.opms.main.purchase.model;

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

public class BookPurchaseMapper {

    /**
     * Transform a {@link UserInfoResponse} into an {@link UserInfoDomain}.
     *
     * @param jsonBookData Object to be transformed.
     * @return {@link UserInfoDomain} if valid {@link UserInfoResponse} otherwise null.
     */
    public static BookPurchaseDomain transform(String jsonBookData) {
        BookPurchaseDomain bookDomain = new BookPurchaseDomain();
        if (jsonBookData != null) {
            BookPurchaseData bookData = new Gson().fromJson(jsonBookData, BookPurchaseData.class);
            bookDomain.product_id = bookData.product_id;
            bookDomain.product_title = bookData.product_title;
            bookDomain.product_author = bookData.product_author;
            bookDomain.product_translator = bookData.product_translator;

            bookDomain.cover_image1 = bookData.cover_image1;
            bookDomain.cover_image2 = bookData.cover_image2;
            bookDomain.cover_image3 = bookData.cover_image3;
            bookDomain.cover_image4 = bookData.cover_image4;

            bookDomain.is_order = bookData.is_order;
            bookDomain.is_rental = bookData.is_rental;
            bookDomain.setPivot(bookData.pivot.order_id, bookData.pivot.product_id);
        }
        return bookDomain;
    }

    public static BookPurchaseDomain transform(BookPurchaseData bookPurchaseData) {
        BookPurchaseDomain bookPurchaseDomain = new BookPurchaseDomain();
        if (bookPurchaseData != null) {
            bookPurchaseDomain.product_id = bookPurchaseData.product_id;
            bookPurchaseDomain.product_title = bookPurchaseData.product_title;
            bookPurchaseDomain.product_author = bookPurchaseData.product_author;
            bookPurchaseDomain.product_translator = bookPurchaseData.product_translator;

            bookPurchaseDomain.cover_image1 = bookPurchaseData.cover_image1;
            bookPurchaseDomain.cover_image2 = bookPurchaseData.cover_image2;
            bookPurchaseDomain.cover_image3 = bookPurchaseData.cover_image3;
            bookPurchaseDomain.cover_image4 = bookPurchaseData.cover_image4;

            bookPurchaseDomain.is_order = bookPurchaseData.is_order;
            bookPurchaseDomain.is_rental = bookPurchaseData.is_rental;
            bookPurchaseDomain.setPivot(bookPurchaseData.pivot.order_id, bookPurchaseData.pivot.product_id);
        }

        return bookPurchaseDomain;
    }

    public static List<BookPurchaseDomain> transformList(String jsonBooks) {
        List<BookPurchaseDomain> booksDomain = new ArrayList<BookPurchaseDomain>();

        List<BookPurchaseData> booksData = new GsonBuilder().setPrettyPrinting().create()
                .fromJson(jsonBooks, new TypeToken<List<BookPurchaseData>>() {
                }.getType());
        BookPurchaseDomain itemDomain;
        Debug.i("TAG_TAG", "==========hhhhhhhhh");
        Debug.i("TAG_TAG", "pivot.order_id=" + booksData.get(0).pivot.order_id +
                " - pivot.product_id=" + booksData.get(0).pivot.product_id);
        for (BookPurchaseData item : booksData) {
            itemDomain = transform(item);
            if (itemDomain != null) {
                booksDomain.add(itemDomain);
            }
        }
        return booksDomain;
    }
}
