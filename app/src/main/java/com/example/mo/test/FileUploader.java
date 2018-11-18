package com.example.mo.test;

import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.util.Calendar;

public class FileUploader extends StorageInteraction {
    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;" +
            "AccountName=mogeneral;" +
            "AccountKey=WfpQSA5fHzhezp0OCjjVnJjNytggrmaBv+BUjIX4+JH6c75kPflcQu0RTIMUTSPeBPmGEyubnaRPDHL8OOLoIw==;" +
            "EndpointSuffix=core.windows.net";

    public static String UploadVideo(String fileUri, String username) throws Exception {
        return Upload(fileUri, getVideoFileName(username), getVideoContainer());
    }

    public static String UploadAudio(String fileUri, String username) throws Exception {
        return Upload(fileUri, getAudioFileName(username), getAudioContainer());
    }

    private static String Upload(String fileUri, String fileName, CloudBlobContainer container) throws Exception {
        container.createIfNotExists();
        CloudBlockBlob fileBlob = container.getBlockBlobReference(fileName);
        fileBlob.uploadFromFile(fileUri);
        return fileBlob.getUri().toString();
    }

    private static String getVideoFileName(String username) {
        return username + "-" +  getFileName().concat(".mp4");
    }

    private static String getAudioFileName(String username) {
        return username + "-" + getFileName().concat(".mp3");
    }

    private static String getFileName() {
        return String.valueOf(Calendar.getInstance().getTime());
    }
}
