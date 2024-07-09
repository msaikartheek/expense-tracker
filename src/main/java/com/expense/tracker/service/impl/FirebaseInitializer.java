package com.expense.tracker.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitializer {

    @Value("${firebase.projectId}")
    private String projectId;

    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Value("${firebase.storage.serviceAccountKeyPath}")
    private String serviceAccountKeyPath;

    @PostConstruct
    public void initialize() throws IOException {

//        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
//        Blob blob = storage.get(bucketName, serviceAccountKeyPath);
//        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(blob.getContent());
        InputStream serviceAccount = new FileInputStream(serviceAccountKeyPath);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                //.setStorageBucket(bucketName)
                .build();
//
        FirebaseApp.initializeApp(options);
    }
}
