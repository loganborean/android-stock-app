package com.example.logan.stockapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
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

        intent = getIntent();

        stockSymbol = intent.getStringExtra("stockSymbol");
        String url = "http://download.finance.yahoo.com/d/quotes.csv?s=" +
                stockSymbol + "&f=nsl1t1cpoba&e=.csv";

        RetrieveStockTask task = new RetrieveStockTask();
        task.execute(url);

    }

    private class RetrieveStockTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... url) {

            URL yahoo = null;
            String dataLine = null;
            try {
                yahoo = new URL(url[0]);
            } catch (MalformedURLException e) {
                Log.d("~~~~~~~~~~~~~~~~~~~~~~~", "malformed url");
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(
                        new InputStreamReader(yahoo.openStream()));
            } catch (IOException e) {
                Log.d("~~~~~~~~~~~~~~~~~~~~~~~", "io ex");
            }

            String inputLine;
            try {
                while ((inputLine = in.readLine()) != null) {
                    dataLine = inputLine;
                }
            } catch (IOException e) {
                Log.d("~~~~~~~~~~~~~~~~~~~~~~~", "2nd io ex");
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.d("~~~~~~~~~~~~~~~~~~~~~~~", "close ex");
            }
            return dataLine;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String[] data = getParsedCsvData(result);

            data = getDataNoQuotes(data);

            displayData(data);

            WebView web = (WebView)findViewById(R.id.webView2);
            web.getSettings().setDomStorageEnabled(true);
            web.getSettings().setJavaScriptEnabled(true);
            web.loadUrl("file:///android_asset/stock_widget.html");
        }

    }
    private String[] getDataNoQuotes(String[] data) {
        for(int i = 0; i < data.length; i++)
            data[i] = data[i].replace("\"", "");
        return data;
    }

    private String[] getParsedCsvData(String dataLine) {
        String otherThanQuote = " [^\"] ";
        //yes please some more
        String quotedString = String.format(" \" %s* \" ", otherThanQuote);
        String regex = String.format("(?x) "+ // enable comments, ignore white spaces
                        ",                         "+ // match a comma
                        "(?=                       "+ // start positive look ahead
                        "  (                       "+ //   start group 1
                        "    %s*                   "+ //     match 'otherThanQuote' zero or more times
                        "    %s                    "+ //     match 'quotedString'
                        "  )*                      "+ //   end group 1 and repeat it zero or more times
                        "  %s*                     "+ //   match 'otherThanQuote'
                        "  $                       "+ // match the end of the string
                        ")                         ", // stop positive look ahead
                otherThanQuote, quotedString, otherThanQuote);

        return dataLine.split(regex, -1);
    }

    private void displayData(String[] data) {
        TextView messageView;

        messageView = (TextView) findViewById(R.id.stockName);
        String text = data[0];
        messageView.setText(text);

        messageView = (TextView) findViewById(R.id.symbol);
        text = data[1];
        messageView.setText(text);

        messageView = (TextView) findViewById(R.id.lastTrade);
        text =  data[2];
        messageView.setText(text);

        messageView = (TextView) findViewById(R.id.tradeTime);
        text =  data[3];
        messageView.setText(text);

        messageView = (TextView) findViewById(R.id.change);
        text =  data[4];
        messageView.setText(text);

        messageView = (TextView) findViewById(R.id.prevClose);
        text =  data[5];
        messageView.setText(text);

        messageView = (TextView) findViewById(R.id.open);
        text =  data[6];
        messageView.setText(text);

        messageView = (TextView) findViewById(R.id.bid);
        text =  data[7];
        messageView.setText(text);

        messageView = (TextView) findViewById(R.id.ask);
        text =  data[8];
        messageView.setText(text);

    }

}
