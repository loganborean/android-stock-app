package com.example.logan.stockapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Stock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        final Intent intent;
        final String stockSymbol;
//        final TextView messageView;
//
        intent = getIntent();
        stockSymbol = intent.getStringExtra("stockSymbol");
//        messageView = (TextView) findViewById(R.id.stockDispaly);
//        messageView.setText(stockSymbol);
        String url = "http://download.finance.yahoo.com/d/quotes.csv?s=" +
                stockSymbol + "&f=nsl1t1cpoba&e=.csv";
        new RetrieveStockTask().execute(url);


    }

    private class RetrieveStockTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... url) {

            URL oracle = null;
            try {
                oracle = new URL(url[0]);
            } catch (MalformedURLException e) {
                Log.d("~~~~~~~~~~~~~~~~~~~~~~~", "malformed url");
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(
                        new InputStreamReader(oracle.openStream()));
            } catch (IOException e) {
                Log.d("~~~~~~~~~~~~~~~~~~~~~~~", "io ex");
            }

            String inputLine;
            try {
                while ((inputLine = in.readLine()) != null)
                    Log.d("~~~~~~~~~~~~~~~~~~~~~~~", inputLine);
            } catch (IOException e) {
                Log.d("~~~~~~~~~~~~~~~~~~~~~~~", "2nd io ex");
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.d("~~~~~~~~~~~~~~~~~~~~~~~", "close ex");
            }
            return "hello";
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            final Intent intent;
            final String stockSymbol;
            final TextView messageView;

            intent = getIntent();
            stockSymbol = intent.getStringExtra("stockSymbol");
            messageView = (TextView) findViewById(R.id.stockDispaly);
            messageView.setText(stockSymbol);

        }

    }


}
