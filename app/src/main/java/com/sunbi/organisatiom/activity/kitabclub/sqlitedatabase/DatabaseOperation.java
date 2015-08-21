package com.sunbi.organisatiom.activity.kitabclub.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sunbi.organisatiom.activity.kitabclub.models.SQLiteData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokarna on 8/20/15.
 */
public class DatabaseOperation extends SQLiteOpenHelper {
    private Context context;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cartinfo";

    // Contacts table name
    private static final String TABLE_NAME = "cartdata";

    // Contacts Table Columns names
    private static final String ID = "id";
    private static final String BOOK_NAME = "book_name";
    private static final String BOOK_IMAGE = "book_image";
    private static final String BOOK_QUANTITY = "book_quantity";
    private static final String BOOK_PRICE = "book_price";

    public DatabaseOperation(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY," + BOOK_NAME + " TEXT,"
                + BOOK_IMAGE + " TEXT," + BOOK_QUANTITY + " TEXT," + BOOK_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addRecord(SQLiteData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, data.getBookName()); // book Name
        values.put(BOOK_IMAGE, data.getBookImage()); // book image
        values.put(BOOK_QUANTITY, data.getBookQuantity()); // book quantity
        values.put(BOOK_PRICE, data.getBookPrice()); // book price
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public int countDataEntryFromDataBase() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    // Getting All Data
    public List<SQLiteData> getAllData() {
        List<SQLiteData> dataList = new ArrayList<SQLiteData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SQLiteData data = new SQLiteData();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setBookName(cursor.getString(1));
                Log.d("Book_Name", cursor.getString(1));
                data.setBookImage(cursor.getString(2));
                Log.d("Book_Image", cursor.getString(2));
                data.setBookQuantity(cursor.getString(3));
                Log.d("Book_Quantity", cursor.getString(3));
                data.setBookPrice(cursor.getString(4));
                Log.d("Book_Price", cursor.getString(4));
                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;
    }

    // Deleting single data
    public void deleteData(SQLiteData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }
}
