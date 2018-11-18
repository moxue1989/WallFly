package com.example.mo.test;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity {
    private FirebaseAuth mAuth;

    private EditText _etUsername;
    private EditText _etPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _etUsername = findViewById(R.id._loginUsername);
        _etPassword = findViewById(R.id._loginPassword);

        _etUsername.setText("mo_xue1989@yahoo.ca");
        _etPassword.setText("password");

        Button _loginBtn = findViewById(R.id._btnSignIn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_etUsername.getText().toString().isEmpty()||
                        _etPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "You must enter a username and password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mAuth == null) {
                    mAuth = FirebaseAuth.getInstance();
                }
                mAuth.signInWithEmailAndPassword(_etUsername.getText().toString(), _etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(), user.getEmail(),
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                                    i.putExtra("_username", _etUsername.getText().toString());
                                    _etPassword.setText("");
                                    _etUsername.setText("");
                                    startActivity(i);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
