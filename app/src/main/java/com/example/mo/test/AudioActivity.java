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
    private boolean isPlaying = false;

    private MediaRecorder mRecorder = null;

    private MediaPlayer mPlayer = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

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

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                findViewById(R.id.fab_record).setOnClickListener(recordClickListener);
                findViewById(R.id.fab_save).setOnClickListener(saveClickListener);
                Toast.makeText(getApplicationContext(), "Audio has ended",
                        Toast.LENGTH_LONG).show();
            }
        });
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
            Toast.makeText(getApplicationContext(), "Audio has begun",
                    Toast.LENGTH_LONG).show();
            isPlaying = true;
            findViewById(R.id.fab_record).setOnClickListener(null);
            findViewById(R.id.fab_save).setOnClickListener(null);
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
            Toast.makeText(getApplicationContext(), "There was a problem playing your audio",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
        Toast.makeText(getApplicationContext(), "Audio has ended",
                Toast.LENGTH_LONG).show();
        isPlaying = false;
        findViewById(R.id.fab_record).setOnClickListener(recordClickListener);
        findViewById(R.id.fab_save).setOnClickListener(saveClickListener);
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
                Toast.LENGTH_LONG).show();
        isRecording = true;
        findViewById(R.id.fab_play).setOnClickListener(null);
        findViewById(R.id.fab_save).setOnClickListener(null);
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        Toast.makeText(getApplicationContext(), "Recording has ended",
                Toast.LENGTH_LONG).show();
        isRecording = false;
        findViewById(R.id.fab_play).setOnClickListener(playClickListener);
        findViewById(R.id.fab_save).setOnClickListener(saveClickListener);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_audio);
        // Record to the external cache directory for visibility
        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/audioRecord.3gp";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        findViewById(R.id.fab_record).setOnClickListener(recordClickListener);
        findViewById(R.id.fab_play).setOnClickListener(playClickListener);
        findViewById(R.id.fab_save).setOnClickListener(saveClickListener);
        findViewById(R.id.fab_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    stopPlaying();
                }
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

    private View.OnClickListener saveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mFileName != null) {
                try {
                    Toast.makeText(getApplicationContext(), "Your upload has begun",
                            Toast.LENGTH_LONG).show();
                    new UploadAudioTask(){
                        @Override
                        protected void onPostExecute(Long aLong) {
                            super.onPostExecute(aLong);
                            Toast.makeText(getApplicationContext(), "Your upload has finished",
                                    Toast.LENGTH_LONG).show();
                            updateAdapter();
                        }
                    }.execute(mFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

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

    private View.OnClickListener playClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isPlaying) {
                startPlaying();
            } else {
                stopPlaying();
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
