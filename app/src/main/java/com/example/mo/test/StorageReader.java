package com.example.mo.test;

import android.app.Activity;
import android.widget.Toast;

import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

import java.util.ArrayList;
import java.util.List;

public class StorageReader extends StorageInteraction {
    public static Iterable<String> getFileList(Activity context) {
        List<String> fileList = new ArrayList<>();
        try {
            for (ListBlobItem blobItem : getVideoContainer().listBlobs()) {
                if (blobItem instanceof CloudBlockBlob) {
                    CloudBlockBlob retrievedBlob = (CloudBlockBlob) blobItem;
                    fileList.add(retrievedBlob.getName().replace('-', '\n'));
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "There was a problem connecting to your account",
                    Toast.LENGTH_SHORT).show();
        }
        return fileList;
    }
    public static Iterable<String> getAudioFileList(Activity context) {
        List<String> fileList = new ArrayList<>();
        try {
            for (ListBlobItem blobItem : getAudioContainer().listBlobs()) {
                if (blobItem instanceof CloudBlockBlob) {
                    CloudBlockBlob retrievedBlob = (CloudBlockBlob) blobItem;
                    fileList.add(retrievedBlob.getName());
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "There was a problem connecting to your account",
                    Toast.LENGTH_SHORT).show();
        }
        return fileList;
    }
}
