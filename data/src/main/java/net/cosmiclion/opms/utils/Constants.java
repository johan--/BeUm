package net.cosmiclion.opms.utils;

import android.os.Environment;

/**
 * Created by longpham on 9/18/2016.
 */
public class Constants {
    public static final String DATABASE_NAME = "OPMS_DB.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TEXT_TYPE = " TEXT";
    public static final String BLOB_TYPE = "BLOB";
    public static final String INT_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";

    public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/OPMS";
}
