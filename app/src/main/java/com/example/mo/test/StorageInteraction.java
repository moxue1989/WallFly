package com.example.mo.test;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;

public abstract class StorageInteraction {
    static final String FOLDER = "Test";
    static final String CONTAINER_NAME="leftbeef";
    static CloudBlobContainer getContainer() throws Exception {
        CloudStorageAccount storageAccount = CloudStorageAccount
                .parse(DbConnection.storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        return blobClient.getContainerReference(CONTAINER_NAME);
    }
    static CloudBlobContainer getFolder() throws Exception {
        CloudStorageAccount storageAccount = CloudStorageAccount
                .parse(DbConnection.storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        return blobClient.getContainerReference(CONTAINER_NAME);
    }
}
