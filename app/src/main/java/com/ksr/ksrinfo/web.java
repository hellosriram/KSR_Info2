package com.ksr.ksrinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class web extends AppCompatActivity {

    private WebView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        browser=findViewById(R.id.webview);

        Intent intent=getIntent();
        String browserlink= intent.getStringExtra("browserlink");
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);
        browser.loadUrl(browserlink);
    }
}