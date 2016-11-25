package com.example.logan.stockapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavouritesHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cst_courses.db";
    private static final int SCHEMA_VERSION = 1;
    private static final String NAME_TABLE_NAME = "favourites";
    private static final String ID_COLUMN_NAME = "_id";
    private static final String NAME_COLUMN_NAME = "name";
    private static FavouritesHelper instance;

    public FavouritesHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA_VERSION);
    }

    public synchronized static FavouritesHelper getInstance(final Context context) {
        if(instance == null) {
            instance = new FavouritesHelper(context.getApplicationContext());
        }

        return instance;
    }

    public void insertFavourite(final SQLiteDatabase db,
                                final String         name) {
        final ContentValues contentValues;

        contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN_NAME, name);
        db.insert(NAME_TABLE_NAME, null, contentValues);
    }
    public void deleteFav(final SQLiteDatabase db,
                          final String         name) {
        final int rows;

        rows = db.delete(NAME_TABLE_NAME,
                  NAME_COLUMN_NAME + " = ?",
                  new String[]
                  {
                     name,
                  });
        Log.d("~~~~~~", "rows: " + rows);

    }

    public int deleteAll(final SQLiteDatabase db){
        return db.delete(NAME_TABLE_NAME, null, null);
    }

    public long getNumberOfFavourites(final SQLiteDatabase db) {
        final long numEntries;
        numEntries = DatabaseUtils.queryNumEntries(db, NAME_TABLE_NAME);
        return numEntries;
    }

    public Cursor getAllFavourites(final SQLiteDatabase db) {
        final Cursor cursor;

        cursor = db.query(NAME_TABLE_NAME,
                null,
                null,     // selection, null = *
                null,     // selection args (String[])
                null,     // group by
                null,     // having
                null,     // order by
                null);    // limit

//        cursor.setNotificationUri(context.getContentResolver(), NameContentProvider.CONTENT_URI);

        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "  + NAME_TABLE_NAME + " ( " +
                ID_COLUMN_NAME   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COLUMN_NAME + " TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + NAME_TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
