package com.example.booklistingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookInfoActivity extends AppCompatActivity {
    private WebView webView;
    private ImageView infoImage;
    private TextView infoTitle,infoDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
       /* assert getSupportActionBar() != null;   //null check
        //show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

//       infoImage = findViewById(R.id.info_image);
        infoTitle = findViewById(R.id.info_title);
        infoImage = findViewById(R.id.info_image);
        infoDesc = findViewById(R.id.info_desc);


        String title = getIntent().getStringExtra("title");
        String subtitle = getIntent().getStringExtra("subtitle");
        String webReaderLink = getIntent().getStringExtra("webReaderLink");
        String desc = getIntent().getStringExtra("desc");
        String textInfo = getIntent().getStringExtra("textInfo");
        String thumbnail = getIntent().getStringExtra("thumbnail");

        System.out.println("Total Info From MainActivity " + title + " " + subtitle + " " + webReaderLink + " " + textInfo);
// Set image
        infoTitle.setText(title);
        infoDesc.setText(desc);
        Picasso.with(this).load(thumbnail).
                error(R.drawable.ic_launcher_background).
                noFade().
                into((ImageView) infoImage);
        // TODO Opening Webview
    /*    WebView webView = (WebView) findViewById(R.id.webview);
        String url = getIntent().getStringExtra("webReaderLink");
   // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webView.loadUrl("https://www.google.com");
        webView.loadUrl(url);*/

    }

}
