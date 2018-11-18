package com.example.mo.test;

import android.support.annotation.NonNull;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

public abstract class StorageInteraction {
    static final String VIDEO_CONTAINER_NAME = "video";
    static final String AUDIO_CONTAINER_NAME = "audio";

    static CloudBlobContainer getVideoContainer() throws Exception {
        return getCloudBlobContainer(VIDEO_CONTAINER_NAME);
    }

    static CloudBlobContainer getAudioContainer() throws Exception {
        return getCloudBlobContainer(AUDIO_CONTAINER_NAME);
    }

    @NonNull
    private static CloudBlobContainer getCloudBlobContainer(String containerName) throws URISyntaxException, InvalidKeyException, StorageException {
        CloudStorageAccount storageAccount = CloudStorageAccount
                .parse(StorageConnection.storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
        return blobClient.getContainerReference(containerName);
    }
}
