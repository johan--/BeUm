package net.cosmiclion.opms.ebookreader.skyepub;


import android.util.Log;

import java.io.File;

public class SkySetting {
    public int bookCode;
    public String fontName;
    public int fontSize;
    public int lineSpacing;
    public int foreground;
    public int background;
    public int theme;
    public double brightness;
    public int transitionType;
    public boolean lockRotation;
    public boolean doublePaged;
    public boolean allow3G;
    public boolean globalPagination;

    public static String storageDirectory = null;

    public static String getStorageDirectory() {
        return storageDirectory;
    }

    public static void setStorageDirectory(String directory, String appName) {
        storageDirectory = directory + File.separator;
//        storageDirectory = directory + File.separator + appName;
        Log.d("SkySetting11", storageDirectory);
    }
}
