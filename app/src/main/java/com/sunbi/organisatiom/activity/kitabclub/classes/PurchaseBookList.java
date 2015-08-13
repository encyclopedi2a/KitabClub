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
    ArrayList<String> pdf_names = new ArrayList<String>();
    ArrayList<String> pdf_paths = new ArrayList<String>();

    public PurchaseBookList(Context context,MyBooksInterface booksInterface) {
        this.context = context;
        this.booksInterface=booksInterface;
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
            booksInterface.bookName(pdf_names);
            booksInterface.bookPath(pdf_paths);
        }
        return pdf_names;
    }
}
