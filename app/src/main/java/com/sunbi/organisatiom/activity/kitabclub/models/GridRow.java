package com.sunbi.organisatiom.activity.kitabclub.models;

/**
 * Created by gokarna on 8/6/15.
 */
public class GridRow {
    private String id;
    private String name;
    private String image_path;
    private String total_books;
    private String bookPath;
    private String authorName;
    private String price;
    private String discount;
    private String type;
    private String description;
    public GridRow(String id, String name, String image_path, String total_Books) {
        this.id = id;
        this.name = name;
        this.image_path = image_path;
        this.total_books = total_Books;
    }

    public String getDescription() {
        return description;
    }

    public GridRow(String id, String name, String image_path, String bookPath,
                   String authorName, String price, String discount, String type,String description) {
        this.id = id;
        this.name = name;
        this.image_path = image_path;
        this.bookPath = bookPath;
        this.authorName = authorName;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.description=description;

    }

    public String getBookPath() {
        return bookPath;
    }

    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
