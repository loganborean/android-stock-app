package com.example.logan.stockapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    FavouritesHelper favHelper;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.insertDefaults();

        Button buttonOne = (Button) findViewById(R.id.button);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                final EditText messageView;
                final Intent intent;
                final String stockSymbol;

                messageView = (EditText)findViewById(R.id.stockText);
                stockSymbol = messageView.getText().toString();
                intent = new Intent(getApplicationContext(), Stock.class);
                intent.putExtra("stockSymbol", stockSymbol);
                startActivity(intent);
                finish();
            }
        });

        web = (WebView)findViewById(R.id.webView);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.addJavascriptInterface(new JavaScriptInterface(this), "Android");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        web.loadUrl("file:///android_asset/widget.html");

    }
    public void refresh() {

        web.reload();
    }

    protected void insertDefaults() {
        favHelper = FavouritesHelper.getInstance(getApplicationContext());
        SQLiteDatabase db = favHelper.getWritableDatabase();
        long numEntries = favHelper.getNumberOfFavourites(db);

        if (numEntries == 0) {
            Log.d("~~~~~", "insertig");
            favHelper.insertFavourite(db, "GOOG");
            favHelper.insertFavourite(db, "AAPL");
            favHelper.insertFavourite(db, "AMZN");
            favHelper.insertFavourite(db, "YHOO");
        }
        db.close();
    }
}
