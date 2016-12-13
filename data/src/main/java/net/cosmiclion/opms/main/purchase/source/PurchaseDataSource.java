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

package net.cosmiclion.opms.main.purchase.source;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.login.model.ResponseData;
import net.cosmiclion.opms.main.purchase.model.BookPurchaseData;

import java.util.List;


public interface PurchaseDataSource {

    interface LoadPurchaseCallback {

        void onBooksPurchaseLoaded(ResponseData response);

        void onDataNotAvailable(String errorMessage);
    }

    void getBooksPurchaseResponse(@NonNull String token,
                                  @NonNull LoadPurchaseCallback callback);

    void doSaveBooks(List<BookPurchaseData> books);

    BookPurchaseData getBook(@NonNull String bookId);

    void doUpdateBookDownloaded(@NonNull String bookId);

    void doUpdateBookReaded(@NonNull String bookId);
    }
