package com.sunbi.organisatiom.activity.kitabclub.classes;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.stripe.android.compat.AsyncTask;
import com.sunbi.organisatiom.activity.kitabclub.MyBooks;

import java.io.File;

/**
 * Created by gokarna on 10/1/15.
 */
public class ImageDownloader extends AsyncTask<Void, Void, Void> {
    private Context context;
    private ProgressDialog progressDialog;
    private String imagePath, bookPath, bookName;
    private IntentFilter downloadCompleteIntentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
    private BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intent1=new Intent(context, MyBooks.class);
            context.startActivity(intent1);
        }
    };

    public ImageDownloader(Context context, String bookName, String imagePath, String bookPath) {
        this.context = context;
        //this is done to filter the path in imagepath and bookpath
        this.bookName = bookName;
        this.imagePath = imagePath;
        this.bookPath = bookPath;
        context.registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter);

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
        }
        if (success) {
            downloadImage();
            downloadBook();

        } else {
            Toast.makeText(context, "Already Downloaded", Toast.LENGTH_LONG).show();
        }
    }

    public void downloadImage() {
        String URL = "http://thesunbihosting.com/demo/book_store/uploads/book_cover/bxvOIKgfGaE7.jpg";
        DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(URL);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        long id=mgr.enqueue(request);
        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(bookName)
                .setDescription("Downloading " + bookName)
                .setDestinationInExternalPublicDir("/kitabclub/" + bookName, bookName + ".jpg");
        mgr.enqueue(request);
    }

    public void downloadBook() {
        String URL = "http://thesunbihosting.com/demo/book_store/uploads/book_pdf/vPVyGE19Snd7.epub";
        DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(URL);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(bookName)
                .setDescription("Downloading " + bookName)
                .setDestinationInExternalPublicDir("/kitabclub/" + bookName, bookName + ".epub");
        mgr.enqueue(request);
    }

    private void registerBroadcastReceiverForDownloadingContent() {

    }

}
