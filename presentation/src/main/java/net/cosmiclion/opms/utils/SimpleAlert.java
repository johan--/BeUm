package net.cosmiclion.opms.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Window;

/**
 * Created by kein on 2016. 4. 3..
 */
public class SimpleAlert {
    // thay bang dialog fragment

    public static void show(Activity act, int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }
    public static void show(Activity act, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }
}
