/*
 * DownloadDocumentTask.java
 *   PSPDFKit
 *
 *   Copyright (c) 2014-2016 PSPDFKit GmbH. All rights reserved.
 *
 *   THIS SOURCE CODE AND ANY ACCOMPANYING DOCUMENTATION ARE PROTECTED BY INTERNATIONAL COPYRIGHT LAW
 *   AND MAY NOT BE RESOLD OR REDISTRIBUTED. USAGE IS BOUND TO THE PSPDFKIT LICENSE AGREEMENT.
 *   UNAUTHORIZED REPRODUCTION OR DISTRIBUTION IS SUBJECT TO CIVIL AND CRIMINAL PENALTIES.
 *   This notice may not be removed from this file.
 */

package net.cosmiclion.opms.utils.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * This downloads a document from an input stream
 */
public class DownloadDocumentTask extends AsyncTask<Uri, Integer, Uri> {

    private static final String LOG_TAG = "DownloadDocumentTask";

    public interface DownloadedFileCallback {
        void onFileDownloaded(Uri uri);
    }

    private final Context ctx;
    private ProgressDialog dialog;
    private final DownloadedFileCallback callback;

    public DownloadDocumentTask(Context ctx, DownloadedFileCallback callback) {
        Log.d(LOG_TAG, "Construct");
        this.ctx = ctx;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (ctx != null) {
            Log.d(LOG_TAG, "ctx != null");
            dialog = new ProgressDialog(ctx);
            dialog.setTitle("Downloading");
            dialog.setMessage("Downloading file....");
//            dialog.setIndeterminate(false);
//            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            dialog.setMax(100);
//            dialog.setProgress(0);
            dialog.show();
        }
    }

    @Override
    protected Uri doInBackground(Uri... params) {
        Uri uri = params[0];
        File tempFile = new File(ctx.getCacheDir(), String.valueOf(System.currentTimeMillis()) + ".pdf");

        Log.d(LOG_TAG, "Downloading " + uri + " to " + tempFile);
        try {
            FileOutputStream fos = new FileOutputStream(tempFile);
            AssetFileDescriptor afd = ctx.getContentResolver().openAssetFileDescriptor(uri, "r");

            int read;
            byte[] buffer = new byte[8192];

            FileInputStream ios = afd.createInputStream();

            long len = afd.getLength();
            long totalRead = 0;

            while ((read = ios.read(buffer)) > 0) {
                fos.write(buffer, 0, read);
                totalRead += read;

                publishProgress((int) (((double) totalRead / (double) len) * 100.0));
            }

            ios.close();
            fos.close();

            Log.d(LOG_TAG, "Download complete.");

        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }

        return Uri.fromFile(tempFile);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d(LOG_TAG, values + "");
//        dialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Uri uri) {
        super.onPostExecute(uri);
        if (dialog.isShowing())
            dialog.dismiss();
        callback.onFileDownloaded(uri);
    }
}
