package com.example.multipleemaillogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.example.multipleemaillogin.model.EmailData;

import java.util.ArrayList;

public class ProcessActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        String url = getIntent().getStringExtra("loginUrl");
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(ProcessActivity.this, WebViewActivity.class);
            intent.putExtra("loginUrl", url);
            startActivity(intent);
            finish(); // Close LoadingActivity
        }, 2000);
    }
}