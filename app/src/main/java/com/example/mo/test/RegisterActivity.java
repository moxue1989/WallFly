package com.example.mo.test;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    private DbConnection dbHelper;
    private SQLiteDatabase db;

    private EditText _etUsername;
    private EditText _etPassword;
    private EditText _etPasswordVerify;

    // Registration Activity! *S
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DbConnection(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        // Edit text field for Age, Name, Username and Password
        // Can add some extras here. Need to update UI
        _etUsername = (EditText) findViewById(R.id._username);

        _etPassword = (EditText) findViewById(R.id._password);
        _etPasswordVerify = (EditText) findViewById(R.id._passwordVerify);

        final TextView _tvCancel = (TextView) findViewById(R.id._tvCancel);
        _tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.isOpen()) {
                    db.close();
                }
                dbHelper.close();
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });

        // Button to register if not already
        final Button _btnRegister = (Button) findViewById(R.id._btnRegister);
        _btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasValidUsername()) {
                    if (hasMatchingPassword()) {
                        //Checked for legal and unique username
                        //Checked matching password
                        //Allowed to register

                        // Create a new map of values, where column names are the keys
                        ContentValues values = new ContentValues();
                        values.put(user_schema.columns.COLUMN_USERNAME, _etUsername.getText().toString());
                        values.put(user_schema.columns.COLUMN_PASSWORD_HASHED, _etPassword.getText().toString());

                        // Insert the new row, returning the primary key value of the new row
                        long newRowId = db.insert(user_schema.columns.TABLE_NAME, null, values);
                        db.close();
                        dbHelper.close();
                        setResult(RESULT_OK, new Intent());
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords must match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean hasValidUsername() {
        if (hasLegalCharacters()) {
            if (isUnique()) {
                return true;
            }
        }
        return false;
    }

    private boolean isUnique() {
        String SQL_SELECT_USERNAME = new StringBuilder("SELECT ")
                .append(user_schema.columns.COLUMN_USERNAME)
                .append(" FROM ")
                .append(user_schema.columns.TABLE_NAME)
                .append(" WHERE ")
                .append(user_schema.columns.COLUMN_USERNAME)
                .append("='")
                .append(_etUsername.getText().toString())
                .append("'").toString();
        Cursor resultSet = db.rawQuery(SQL_SELECT_USERNAME, null);
        if (resultSet.getCount() == 0) {
            resultSet.close();
            return true;
        }
        resultSet.close();
        Toast.makeText(getApplicationContext(), "Username taken", Toast.LENGTH_LONG).show();
        return false;
    }

    private boolean hasLegalCharacters() {
        return true;
    }

    private boolean hasMatchingPassword() {
        return _etPassword.getText().toString()
                .equals(_etPasswordVerify.getText().toString());
    }
}
