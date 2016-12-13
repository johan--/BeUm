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

package net.cosmiclion.opms.main.purchase.source.local;

import android.provider.BaseColumns;

import static net.cosmiclion.opms.utils.Constants.COMMA_SEP;
import static net.cosmiclion.opms.utils.Constants.INT_TYPE;
import static net.cosmiclion.opms.utils.Constants.TEXT_TYPE;

/**
 * The contract used for the db to save the tasks locally.
 */
public final class PurchaseLocalContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private PurchaseLocalContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class Book implements BaseColumns {
        public static final String TABLE_NAME = "BookPurchase";

        public static final String COLUMN_ID = "id_";

        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_PRODUCT_TITLE = "product_title";
        public static final String COLUMN_PRODUCT_AUTHOR = "product_author";
        public static final String COLUMN_FILE_TYPE = "filetype";

        public static final String COLUMN_FILE_NAME = "filename";
        public static final String COLUMN_PRODUCT_TRANSLATOR = "product_translator";
        public static final String COLUMN_COVER_IMAGE = "cover_image1";
        public static final String COLUMN_PURCHASE_TIME = "purchase_time";

        public static final String COLUMN_IS_DOWNLOADED = "is_downloaded";
        public static final String COLUMN_LAST_PAGE = "last_page";
        public static final String COLUMN_READ_DATE = "read_date";

        public static final String SQL_CREATE_PURCHASE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_PRODUCT_ID + TEXT_TYPE + " NOT NULL UNIQUE," +
                        COLUMN_PRODUCT_TITLE + TEXT_TYPE + COMMA_SEP +
                        COLUMN_PRODUCT_AUTHOR + TEXT_TYPE + COMMA_SEP +
                        COLUMN_FILE_TYPE + INT_TYPE + COMMA_SEP +

                        COLUMN_FILE_NAME + TEXT_TYPE + COMMA_SEP +
                        COLUMN_PRODUCT_TRANSLATOR + TEXT_TYPE + COMMA_SEP +
                        COLUMN_COVER_IMAGE + TEXT_TYPE + COMMA_SEP +
                        COLUMN_PURCHASE_TIME + TEXT_TYPE + COMMA_SEP +

                        COLUMN_IS_DOWNLOADED + INT_TYPE + COMMA_SEP +
                        COLUMN_LAST_PAGE + INT_TYPE + COMMA_SEP +
                        COLUMN_READ_DATE + TEXT_TYPE +
                        " )";

        public static final String QUERRY_GET_RECENT_READ_BOOKS =
                COLUMN_READ_DATE + " IS NOT NULL AND " + COLUMN_IS_DOWNLOADED + " = ?";

        public static final String QUERRY_GET_RECENT_PURCHASE_BOOKS =
                COLUMN_IS_DOWNLOADED + " = ? ";
    }
}
