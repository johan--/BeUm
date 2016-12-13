/*
 * ExtractAssetTask.java
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
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import net.cosmiclion.opms.utils.Debug;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ExtractAssetTask extends AsyncTask<String, Integer, Uri> {
    private static final String LOG_TAG = "ExtractAssetTask";

    private final Context ctx;
    private ProgressDialog dialog;
    private final DownloadDocumentTask.DownloadedFileCallback callback;

    public ExtractAssetTask(Context ctx, DownloadDocumentTask.DownloadedFileCallback callback) {
        Debug.i(LOG_TAG, "Construct");
        this.ctx = ctx;
        this.callback = callback;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog = new ProgressDialog(this.ctx);
        this.dialog.setTitle("Extracting");
        this.dialog.setMessage("Extracting file....");
//        this.dialog.setIndeterminate(false);
//        this.dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        this.dialog.setMax(100);
//        this.dialog.setProgress(0);
        if (!this.dialog.isShowing()) {
            this.dialog.show();
        }
    }

    @Override
    protected Uri doInBackground(String... params) {

        for (String asset : params) {
            try {
                InputStream is = ctx.getResources().getAssets().open(asset);
                File f = new File(ctx.getFilesDir().getAbsolutePath(), asset);
                Debug.i(LOG_TAG, "getFilesDir=" + ctx.getFilesDir().getAbsolutePath());
                Debug.i(LOG_TAG, "getFilesDir_F=" + f.getAbsolutePath());
                OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
                byte[] buffer = new byte[8192];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }

                os.close();
                is.close();

                return Uri.fromFile(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d(LOG_TAG, values + "");
//        dialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Uri uri) {
        if (dialog.isShowing())
            dialog.dismiss();
        if (callback != null)
            callback.onFileDownloaded(uri);
    }
}
