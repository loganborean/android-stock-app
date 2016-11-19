package com.example.logan.stockapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            }
        });

        WebView web = (WebView)findViewById(R.id.webView);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("file:///android_asset/widget.html");

    }
}
