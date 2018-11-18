package com.example.mo.test;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;

public class UploadVideoTask extends AsyncTask<String, Integer, Long> {
    private MainActivity activity;

    public UploadVideoTask(MainActivity mainActivity) {
        activity = mainActivity;
    }

    @Override
    protected Long doInBackground(String... strings) {
        for (String url : strings) {
            try {
                FileUploader.UploadVideo(url);
                new File(url).delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 1L;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        Toast.makeText(activity, "Video Uploaded and deleted off your device", Toast.LENGTH_SHORT).show();
        activity.updateAdapter();
    }
}
