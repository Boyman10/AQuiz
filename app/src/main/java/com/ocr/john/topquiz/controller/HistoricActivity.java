package com.ocr.john.topquiz.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;

// import the resources :
import com.ocr.john.topquiz.R;

public class HistoricActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);

        // We will put an html page here to display our text
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/page.html");

        // https://developer.android.com/guide/webapps/webview.html#UsingJavaScript

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}
