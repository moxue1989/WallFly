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

    private static CloudBlobContainer getContainer() throws Exception {
        CloudStorageAccount storageAccount = CloudStorageAccount
                .parse(storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        return blobClient.getContainerReference("leftbeef");
    }

    public static String UploadImage(String fileUri) throws Exception {
        CloudBlobContainer container = getContainer();
        container.createIfNotExists();
        String imageName = getFileName("Test");
        CloudBlockBlob imageBlob = container.getBlockBlobReference(imageName);
        imageBlob.uploadFromFile(fileUri);

        return imageBlob.getUri().toString();
    }

    private static String getFileName(String fileName){
        StringBuilder sb = new StringBuilder();
        sb.append(fileName + "/");
        sb.append(Calendar.getInstance().getTime());
        return sb.toString();
    }
}
