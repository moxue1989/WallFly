package com.example.mo.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
    }

    public void quitApp(View view){
        finish();
        System.exit(0);
    }

    public void gotoMenu(View view){
        Intent mainMenu = new Intent(this, MenuActivity.class);
        startActivity(mainMenu);
    }

}
