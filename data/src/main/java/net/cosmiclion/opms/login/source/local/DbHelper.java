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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.cosmiclion.opms.main.purchase.source.local.PurchaseLocalContract;

import static net.cosmiclion.opms.login.source.local.UserInfoContract.Entry.SQL_CREATE_USER_INFO_TABLE;
import static net.cosmiclion.opms.main.purchase.source.local.PurchaseLocalContract.Book.SQL_CREATE_PURCHASE_TABLE;
import static net.cosmiclion.opms.utils.Constants.DATABASE_NAME;
import static net.cosmiclion.opms.utils.Constants.DATABASE_VERSION;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_INFO_TABLE);
        db.execSQL(SQL_CREATE_PURCHASE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + PurchaseLocalContract.Book.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserInfoContract.Entry.TABLE_NAME);
        // create new table
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1

    }
}
