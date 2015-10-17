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
 * Created by gokarna on 10/11/15.
 */
public class PushNotificationData extends SQLiteOpenHelper {
    private Context context;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "pushinfo";

    // Contacts table name
    private static final String TABLE_NAME = "notification";

    // Contacts Table Columns names
    private static final String ID = "id";
    private static final String NOTIFICATION = "book_name";
    private static final String TITLE="title";
    public PushNotificationData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY," + NOTIFICATION + " TEXT,"
                + TITLE + " TEXT" + ")";
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
        values.put(ID,data.getId());
        values.put(TITLE,data.getTitle()); // book Name
        values.put(NOTIFICATION,data.getNotification()); // book image
        // Inserting Row
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
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
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SQLiteData data = new SQLiteData();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setTitle(cursor.getString(2));
                data.setNotification(cursor.getString(1));
                Log.d("Cursor value name:",cursor.getString(2));
                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
            db.close();
        }

        // return contact list
        return dataList;
    }
}
