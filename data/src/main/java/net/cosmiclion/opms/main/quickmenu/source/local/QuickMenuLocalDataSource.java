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

package net.cosmiclion.opms.main.quickmenu.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import net.cosmiclion.opms.main.quickmenu.QuickMenuDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class QuickMenuLocalDataSource implements QuickMenuDataSource {

    private static QuickMenuLocalDataSource INSTANCE;
    private Context mContext;

    private QuickMenuLocalDataSource(@NonNull Context context) {
        this.mContext = checkNotNull(context);
    }

    public static QuickMenuLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new QuickMenuLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void getBooksPurchaseResponse(@NonNull String token, @NonNull LoadPurchaseCallback callback) {

    }
}
