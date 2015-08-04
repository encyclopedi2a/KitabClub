package com.sunbi.organisatiom.activity.kitabclub.models;

/**
 * Created by gokarna on 8/4/15.
 */
public class ListRow {
    private int bookImage;
    private String bookName;
    private String imagePath;
    public ListRow(String bookName, String description){
        this.bookImage=bookImage;
        this.bookName=bookName;
    }

    public int getBookImage() {
        return bookImage;
    }

    public void setBookImage(int bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
