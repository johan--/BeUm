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

package net.cosmiclion.opms.main.mylibrary.source;

import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.mylibrary.model.BooksRequest;
import net.cosmiclion.opms.main.mylibrary.model.BooksResponse;


public interface LibraryDataSource {

    interface LoadLibraryCallback {

        void onBooksLoaded(BooksResponse response);

        void onDataNotAvailable(String errorMessage);
    }

    void getBooksResponse(@NonNull BooksRequest request, @NonNull LoadLibraryCallback callback);

}
