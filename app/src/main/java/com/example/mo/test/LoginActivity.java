package com.example.mo.test;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends RegisterActivity {
    private DbConnection dbHelper;
    private SQLiteDatabase db;

    private EditText _etUsername;
    private EditText _etPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DbConnection(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        _etUsername = (EditText) findViewById(R.id._loginUsername);
        _etPassword = (EditText) findViewById(R.id._loginPassword);

        TextView _registerLink = (TextView) findViewById(R.id._tvRegister);
        _registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newRegisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(newRegisterIntent, 1);
            }
        });

        Button _loginBtn = (Button) findViewById(R.id._btnSignIn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasValidCredentials()) {
                    Intent i = new Intent(LoginActivity.this, TwoFactorActivity.class);
                    i.putExtra("_username",_etUsername.getText().toString());
                    _etPassword.setText("");
                    _etUsername.setText("");
                    startActivity(i);
                }
            }
        });
    }

    private boolean hasValidCredentials() {
        String SQL_SELECT_USERNAME = new StringBuilder("SELECT ")
                .append(user_schema.columns.COLUMN_USERNAME)
                .append(" FROM ")
                .append(user_schema.columns.TABLE_NAME)
                .append(" WHERE ")
                .append(user_schema.columns.COLUMN_USERNAME)
                .append("='")
                .append(_etUsername.getText().toString())
                .append("'")
                .append(" AND ")
                .append(user_schema.columns.COLUMN_PASSWORD_HASHED)
                .append("='")
                .append(_etPassword.getText().toString())
                .append("'").toString();
        Cursor resultSet = db.rawQuery(SQL_SELECT_USERNAME, null);
        if (resultSet.getCount() == 0) {
            resultSet.close();
            Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
            return false;
        }
        resultSet.close();
        Toast.makeText(getApplicationContext(), "Proceed to two factor authentication", Toast.LENGTH_LONG).show();
        return true;
    }
}
