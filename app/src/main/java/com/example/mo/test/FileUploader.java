package com.example.mo.test;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.InputStream;
import java.security.SecureRandom;

public class FileUploader {
    public static final String storageConnectionString = "storage key";

    private static CloudBlobContainer getContainer() throws Exception {
        // Retrieve storage account from connection-string.

        CloudStorageAccount storageAccount = CloudStorageAccount
                .parse(storageConnectionString);

        // Create the blob client.
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

        // Get a reference to a container.
        // The container name must be lower case
        CloudBlobContainer container = blobClient.getContainerReference("leftbeef");

        return container;
    }

    public static String UploadImage(String fileUri) throws Exception {
        CloudBlobContainer container = getContainer();

        container.createIfNotExists();

        String imageName = randomString(10);

        CloudBlockBlob imageBlob = container.getBlockBlobReference(imageName);
        imageBlob.uploadFromFile(fileUri);

        return imageBlob.getUri().toString();
    }

    static final String validChars = "abcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( validChars.charAt( rnd.nextInt(validChars.length()) ) );
        return sb.toString();
    }

}
