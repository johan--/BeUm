package net.cosmiclion.opms.utils;

import android.util.Log;

import net.cosmiclion.data.BuildConfig;

/**
 * Created by longpham on 9/14/2016.
 */
public class Debug {
    private static boolean IS_DEBUG = BuildConfig.DEBUG;

    public static void i(String tag, String message) {
        if (IS_DEBUG) {
            if (message == null) {
                message = "";
            } else {
                Log.i(tag, message);
            }

        }
    }
}
