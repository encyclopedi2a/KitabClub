package com.sunbi.organisatiom.activity.kitabclub.models;

/**
 * Created by gokarna on 8/6/15.
 */
public class GridRow {
    private String id;
    private String name;
    private String image_path;
    private String total_books;

    public GridRow(String id, String name, String image_path, String total_Books) {
        this.id = id;
        this.name = name;
        this.image_path = image_path;
        this.total_books = total_Books;
    }

    public String getTotal_books() {
        return total_books;
    }

    public void setTotal_books(String total_books) {
        this.total_books = total_books;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
