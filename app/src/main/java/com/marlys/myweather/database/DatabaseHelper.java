package com.marlys.myweather.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "City.db";
    private static final String DB_TABLE = "City_table";

    private static final String ID = "ID";
    private static final String NAME = "NAME";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + DB_TABLE;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, name);
        long result = db.insert(DB_TABLE, null, contentValues);

        return result != -1;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "Select name from " + DB_TABLE;

        return db.rawQuery(q, null);
    }

    public int deleteRow(String row) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "name = ?";
        String[] args = new String[]{row};
        return db.delete(DB_TABLE, selection, args);
    }
}
