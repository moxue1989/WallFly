package com.example.mo.test;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class AudioActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioActivity";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;

    private boolean isRecording = false;

    private MediaRecorder mRecorder = null;

    private MediaPlayer mPlayer = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private String username;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
        Toast.makeText(getApplicationContext(), "Recording has begun",
                Toast.LENGTH_SHORT).show();
        isRecording = true;
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        Toast.makeText(getApplicationContext(), "Recording has ended",
                Toast.LENGTH_SHORT).show();
        isRecording = false;
        if (mFileName != null) {
            try {
                Toast.makeText(getApplicationContext(), "Your upload has begun",
                        Toast.LENGTH_SHORT).show();
                new UploadAudioTask(username){
                    @Override
                    protected void onPostExecute(Long aLong) {
                        super.onPostExecute(aLong);
                        Toast.makeText(getApplicationContext(), "Audio uploaded and deleted off your device",
                                Toast.LENGTH_SHORT).show();
                        updateAdapter();
                    }
                }.execute(mFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_audio);
        username = getIntent().getStringExtra("_username");
        // Record to the external cache directory for visibility
        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/audioRecord.3gp";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        findViewById(R.id.fab_record).setOnClickListener(recordClickListener);
        findViewById(R.id.fab_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                }
                mFileName = null;
                finish();
            }
        });
        updateAdapter();
    }

    public void updateAdapter() {
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.listitem_file, (List) StorageReader.getAudioFileList(this));

        ListView listView = findViewById(R.id.audio_file_list);
        listView.setAdapter(adapter);
    }

    private View.OnClickListener recordClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isRecording) {
                startRecording();
            } else {
                stopRecording();
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
