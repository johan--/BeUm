package net.cosmiclion.data.utils;

import android.util.Log;

/**
 * Created by longpham on 9/14/2016.
 */
public class Debug {
    private static boolean IS_DEBUG = true;

    public static void i(String tag, String message) {
        if (IS_DEBUG) {
            Log.i(tag, message);
        }
    }
}
