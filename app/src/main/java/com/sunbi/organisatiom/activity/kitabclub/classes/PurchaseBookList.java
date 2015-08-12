package com.sunbi.organisatiom.activity.kitabclub.classes;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by gokarna on 8/12/15.
 */
public class PurchaseBookList {
    private Context context;
    File MEDIA_PATH = null;
    File folder;
    ArrayList<String> pdf_names = new ArrayList<String>();
    ArrayList<String> pdf_paths = new ArrayList<String>();

    public PurchaseBookList(Context context) {
        this.context = context;
        MEDIA_PATH = new File(Environment.getExternalStorageDirectory() + "");
        folder = MEDIA_PATH;
        searchFolderRecursive();
    }

    public ArrayList<String> searchFolderRecursive() {
        if (folder != null) {
            if (folder.listFiles() != null) {
                for (File file : folder.listFiles()) {
                    if (file.isFile()) {
                        if (file.getName().contains(".pdf")) {
                            file.getPath();
                            pdf_names.add(file.getName());
                            pdf_paths.add(file.getPath());
                        }
                    } else {
                        folder = file;
                        searchFolderRecursive();
                    }
                }
            }
        }
        return pdf_names;
    }
}
