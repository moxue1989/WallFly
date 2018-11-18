package com.example.mo.test;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.util.Calendar;

public class FileUploader {
    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;" +
            "AccountName=mogeneral;" +
            "AccountKey=WfpQSA5fHzhezp0OCjjVnJjNytggrmaBv+BUjIX4+JH6c75kPflcQu0RTIMUTSPeBPmGEyubnaRPDHL8OOLoIw==;" +
            "EndpointSuffix=core.windows.net";

    private static CloudBlobContainer getContainer(String containerName) throws Exception {
        CloudStorageAccount storageAccount = CloudStorageAccount
                .parse(storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        return blobClient.getContainerReference(containerName);
    }

    public static String UploadVideo(String fileUri) throws Exception {
        return Upload(fileUri, getVideoFileName(), getContainer("video"));
    }

    private static String Upload(String fileUri, String fileName, CloudBlobContainer container) throws Exception {
        container.createIfNotExists();
        CloudBlockBlob fileBlob = container.getBlockBlobReference(fileName);
        fileBlob.uploadFromFile(fileUri);
        return fileBlob.getUri().toString();
    }

    public static String UploadAudio(String fileUri) throws Exception {
        return Upload(fileUri, getAudioFileName(), getContainer("audio"));
    }

    private static String getVideoFileName() {
        return getFileName().concat(".mp4");
    }

    private static String getAudioFileName() {
        return getFileName().concat(".mp3");
    }

    private static String getFileName() {
        return String.valueOf(Calendar.getInstance().getTime());
    }
}
