package com.sunbi.organisatiom.activity.kitabclub.classes;

import android.content.Context;
import android.os.Environment;

import com.sunbi.organisatiom.activity.kitabclub.interfaces.MyBooksInterface;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by gokarna on 8/12/15.
 */
public class PurchaseBookList {
    private Context context;
    File MEDIA_PATH = null;
    File folder;
    MyBooksInterface booksInterface;
    ArrayList<String> epub_names = new ArrayList<String>();
    ArrayList<String> epub_paths = new ArrayList<String>();
    ArrayList<String> epub_image_path = new ArrayList<>();

    public PurchaseBookList(Context context, MyBooksInterface booksInterface) {
        this.context = context;
        this.booksInterface = booksInterface;
        MEDIA_PATH = new File(Environment.getExternalStorageDirectory() + "");
        folder = MEDIA_PATH;
        searchFolderRecursive();
    }

    public ArrayList<String> searchFolderRecursive() {
        if (folder != null) {
            if (folder.listFiles() != null) {
                for (File file : folder.listFiles()) {
                    if (file.isFile()) {
                        if (file.getName().contains(".epub")) {
                            epub_names.add(file.getName());
                            epub_paths.add(file.getPath());
                            epub_image_path.add(file.getParent());
                        }
                    } else {
                        folder = file;
                        searchFolderRecursive();
                    }
                }
            }
            booksInterface.bookName(epub_names);
            booksInterface.bookPath(epub_paths);
            booksInterface.imagePath(epub_image_path);
        }
        return epub_names;
    }
}
