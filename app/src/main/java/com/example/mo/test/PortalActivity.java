package com.example.mo.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PortalActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);

        final Handler handler = new Handler();
        final Runnable goToLoginScreen = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(PortalActivity.this,LoginActivity.class);
                startActivity(i);
            }
        };
        final Runnable startTimerToRedirect = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(goToLoginScreen, 3000);
            }
        };

        startTimerToRedirect.run();
    }
}