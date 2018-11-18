package com.example.mo.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        username = getIntent().getStringExtra("_username");
    }

    public void gotoVideoDiary(View view){
        Intent videoDiary = new Intent(this, MainActivity.class);
        videoDiary.putExtra("_username", username);
        startActivity(videoDiary);
    }

    public void gotoAudioDiary(View view){
        Intent audioActivity = new Intent(this, AudioActivity.class);
        audioActivity.putExtra("_username", username);
        startActivity(audioActivity);
    }

    public void gotoSettings(View view){
        //Intent settings = new Intent(this, SettingsActivity.class);
        //startActivity(settings);
    }

    public void quitApp(View view){
        finishAffinity();
        System.exit(0);
    }
}
