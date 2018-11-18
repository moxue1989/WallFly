package com.example.mo.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void gotoVideoDiary(View view){
        Intent videoDiary = new Intent(this, MainActivity.class);
        startActivity(videoDiary);
    }

    public void gotoAudioDiary(View view){
        //Intent audioDiary = new Intent(this, AudioDiaryActivity.class);
        //startActivity(audioDiary.class);
    }

    public void gotoSettings(View view){
        //Intent settings = new Intent(this, SettingsActivity.class);
        //startActivity(settings);
    }

    public void quitApp(View view){
        finish();
        System.exit(0);
    }
}
