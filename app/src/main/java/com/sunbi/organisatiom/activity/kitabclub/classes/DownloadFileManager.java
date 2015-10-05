package com.sunbi.organisatiom.activity.kitabclub.classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.stripe.android.compat.AsyncTask;

import java.io.File;

/**
 * Created by gokarna on 10/1/15.
 */
public class DownloadFileManager extends AsyncTask<Void, Void, Void> {
    private Context context;
    private ProgressDialog progressDialog;
    private String imagePath, bookPath, bookName;

    public DownloadFileManager(Context context, String bookName, String imagePath, String bookPath) {
        this.context = context;
        //this is done to filter the path in imagepath and bookpath
        this.bookName = bookName;
        this.imagePath = imagePath;
        this.bookPath = bookPath;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        // Set progressdialog title
        progressDialog.setTitle("Download books and cover");
        // Set progressdialog message
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(false);
        // Show progressdialog
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        prepareRootFolder();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    public void prepareRootFolder() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/kitabclub/" + bookName);
        folder.setReadOnly();
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        } else {
            if (folder.listFiles().length < 2) {
                Toast.makeText(context, "Already Downloaded", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (success) {
            downloadImage();
            downloadBook();

        } else {
            Toast.makeText(context, "Already Downloaded", Toast.LENGTH_LONG).show();
        }
    }

    public void downloadImage() {
        String URL = imagePath;
        Log.d("Image Path", URL);
        android.app.DownloadManager mgr = (android.app.DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(URL);
        android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(
                android.app.DownloadManager.Request.NETWORK_WIFI
                        | android.app.DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(bookName)
                .setDestinationInExternalPublicDir("/kitabclub/" + bookName, "cover.jpg");
        mgr.enqueue(request);
    }

    public void downloadBook() {
        String URL = bookPath;
        Log.d("Book Path", URL);
        android.app.DownloadManager mgr = (android.app.DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(URL);
        android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_WIFI
                | android.app.DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(bookName)
                .setDescription("Downloading " + bookName)
                .setDestinationInExternalPublicDir("/kitabclub/" + bookName, bookName + ".epub");
        mgr.enqueue(request);
    }
}
