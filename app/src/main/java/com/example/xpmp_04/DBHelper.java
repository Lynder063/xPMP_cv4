package com.example.xpmp_04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ItemList.db";

    // Define table for beer list
    private static final String SQL_CREATE_BEERS =
            "CREATE TABLE IF NOT EXISTS " + DBContract.BeerEntry.TABLE_NAME + " (" +
                    DBContract.BeerEntry._ID + " INTEGER PRIMARY KEY," +
                    DBContract.BeerEntry.COLUMN_NAME_PIVO + " TEXT," +
                    DBContract.BeerEntry.COLUMN_NAME_STUPEN + " TEXT," +
                    DBContract.BeerEntry.COLUMN_NAME_AMOUNT + " INTEGER)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBHelper", "onCreate called");
        // Create the table for storing beers
        db.execSQL(SQL_CREATE_BEERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.BeerEntry.TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllBeers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                DBContract.BeerEntry._ID,
                DBContract.BeerEntry.COLUMN_NAME_STUPEN,
                DBContract.BeerEntry.COLUMN_NAME_AMOUNT
        };
        // Query the database for all beers
        return db.query(
                DBContract.BeerEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }


    // Metoda pro získání seznamu stupňů piv
    public List<String> getAllStupne() {
        List<String> stupneList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + DBContract.BeerEntry.COLUMN_NAME_STUPEN + " FROM " + DBContract.BeerEntry.TABLE_NAME, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String stupen = cursor.getString(cursor.getColumnIndex(DBContract.BeerEntry.COLUMN_NAME_STUPEN));
                stupneList.add(stupen);
            }
            cursor.close();
        }
        db.close();
        return stupneList;
    }
    public boolean insertBeer(String stupen, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.BeerEntry.COLUMN_NAME_STUPEN, stupen);
        values.put(DBContract.BeerEntry.COLUMN_NAME_AMOUNT, amount);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.BeerEntry.TABLE_NAME, null, values);
        return newRowId != -1;
    }
    // Metoda pro získání seznamu ID
    public List<String> getAllIDs() {
        List<String> idList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DBContract.BeerEntry._ID + " FROM " + DBContract.BeerEntry.TABLE_NAME, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(DBContract.BeerEntry._ID));
                idList.add(id);
            }
            cursor.close();
        }
        db.close();
        return idList;
    }

    // Metoda pro získání položky piva podle ID
    public BeerItem getBeerItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                DBContract.BeerEntry.COLUMN_NAME_STUPEN,
                DBContract.BeerEntry.COLUMN_NAME_AMOUNT
        };
        String selection = DBContract.BeerEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(
                DBContract.BeerEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        BeerItem beerItem = null;
        if (cursor != null && cursor.moveToFirst()) {
            String stupen = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.BeerEntry.COLUMN_NAME_STUPEN));
            int amount = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.BeerEntry.COLUMN_NAME_AMOUNT));
            beerItem = new BeerItem((long) id, stupen, amount);
            cursor.close();
        }
        db.close();
        return beerItem;
    }

    public boolean updateBeer(int id, String stupen, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.BeerEntry.COLUMN_NAME_STUPEN, stupen);
        values.put(DBContract.BeerEntry.COLUMN_NAME_AMOUNT, amount);
        // Update the row, returning the number of affected rows
        int rowsAffected = db.update(
                DBContract.BeerEntry.TABLE_NAME,
                values,
                DBContract.BeerEntry._ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        return rowsAffected > 0;
    }

    public boolean deleteBeer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the row, returning the number of affected rows
        int rowsAffected = db.delete(
                DBContract.BeerEntry.TABLE_NAME,
                DBContract.BeerEntry._ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        return rowsAffected > 0;
    }


}
