package com.example.mo.test;

import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.util.Calendar;

public class FileUploader extends StorageInteraction {
    public static String UploadImage(String fileUri) throws Exception {
        CloudBlobContainer container = getContainer();
        container.createIfNotExists();
        String imageName = getFileName(FOLDER);
        CloudBlockBlob imageBlob = container.getBlockBlobReference(imageName);
        imageBlob.uploadFromFile(fileUri);

        return imageBlob.getUri().toString();
    }

    private static String getFileName(String folder) {
        StringBuilder sb = new StringBuilder();
        sb.append(folder + "/");
        sb.append(Calendar.getInstance().getTime());
        sb.append(".mp4");
        return sb.toString();
    }
}
