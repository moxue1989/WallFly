package com.example.mo.test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        findViewById(R.id.fab2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                dispatchTakeVideoIntent();
            }
        });
        findViewById(R.id.fab_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        username = getIntent().getStringExtra("_username");
        updateAdapter();
    }

    public void updateAdapter() {
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.listitem_file, (List) StorageReader.getFileList(this));

        ListView listView = findViewById(R.id.file_list);
        listView.setAdapter(adapter);
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            String realPathFromUri = getRealPathFromUri(this, videoUri);

            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 1);

            Toast.makeText(getApplicationContext(), "Your upload has begun: "+realPathFromUri,
                    Toast.LENGTH_LONG).show();
            try {
                new UploadVideoTask(this, username).execute(realPathFromUri);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "There was a problem with your upload",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
