package com.example.mo.test;

import android.os.AsyncTask;

import java.io.File;

public class UploadFileTask extends AsyncTask<String, Integer, Long> {
    @Override
    protected Long doInBackground(String... strings) {
        for (String url : strings) {
            try {
                FileUploader.UploadImage(url);
                new File(url).delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 1L;
    }
}
