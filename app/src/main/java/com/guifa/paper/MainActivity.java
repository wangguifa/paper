package com.guifa.paper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PaperImageView paperImageView = findViewById(R.id.paperImg);
        PaperImage paperImage = new Gson().fromJson(Constant.jsonStr, PaperImage.class);
        paperImageView.loadingPaperImage(paperImage);
        paperImageView.setCallBack(new PaperImageView.CallBackAction() {
            @Override
            public void doAction(String newsId) {
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailIntent.putExtra("newsId", newsId);
                startActivity(detailIntent);
            }
        });
    }
}