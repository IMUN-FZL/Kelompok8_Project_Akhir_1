package com.kelompok8.finalproject1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        // Delay selama 3 detik (3000ms) sebelum berpindah ke activity utama
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent untuk pindah ke activity utama
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Menutup activity splash agar tidak bisa kembali ke sini
            }
        }, 2000);
    }
}
