package com.example.logan.stockapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class JavaScriptInterface {
    Context mContext;
    FavouritesHelper favHelper;

    JavaScriptInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void deleteFav(String stock) {
        favHelper = FavouritesHelper.getInstance(mContext);
        SQLiteDatabase db = favHelper.getWritableDatabase();
        favHelper.deleteFav(db, stock);
        Toast.makeText(mContext, "Favourite Deleted", Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public String getFromAndroid() {
        String symbols = "";

        favHelper = FavouritesHelper.getInstance(mContext);
        SQLiteDatabase db = favHelper.getReadableDatabase();
        Cursor cursor = favHelper.getAllFavourites(db);

        while(cursor.moveToNext()) {
            symbols += cursor.getString(cursor.getColumnIndex("name")) + " ";
        }
        if (symbols.length() > 0 && symbols.charAt(symbols.length()-1)==' ') {

            symbols = symbols.substring(0, symbols.length() - 1);
        }

        return symbols;
    }
}
