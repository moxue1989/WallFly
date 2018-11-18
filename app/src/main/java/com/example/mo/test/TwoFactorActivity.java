package com.example.mo.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class TwoFactorActivity extends Activity {
    private TextView _tvCode;
    private EditText _etVerifyCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twofactor);

        final String _username = getIntent().getStringExtra("_username");
        _tvCode = findViewById(R.id._tvCode);
        _etVerifyCode = findViewById(R.id._etVerifyCode);

        TextView _tvCancelTwoFactor = findViewById(R.id._tvCancelTwoFactor);
        _tvCancelTwoFactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button _btnContinue = findViewById(R.id._btnContinue);
        _btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasValidCode()) {
                    Intent i = new Intent(TwoFactorActivity.this, MenuActivity.class);
                    i.putExtra("_username", _username);
                    Toast.makeText(getApplicationContext(), "Welcome, " + _username, Toast.LENGTH_LONG).show();
                    startActivityForResult(i, 1);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid code", Toast.LENGTH_LONG).show();
                }
            }
        });
        generateRandomCode();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    private boolean hasValidCode() {
        return _etVerifyCode.getText().toString().equals(_tvCode.getText());
    }

    private void generateRandomCode() {
        Random r = new Random();
        String randomCode = new StringBuilder()
                .append(r.nextInt(10))
                .append(r.nextInt(10))
                .append(r.nextInt(10))
                .append(r.nextInt(10)).toString();
        _tvCode.setText(randomCode);
    }
}
