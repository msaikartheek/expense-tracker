package com.expense.tracker.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@Slf4j
public class FirebaseInitializer {

    private final ResourceLoader resourceLoader;

    public FirebaseInitializer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${firebase.projectId}")
    private String projectId;

    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Value("${firebase.storage.serviceAccountKeyPath}")
    private String serviceAccountKeyPath;

    @PostConstruct
    public void initialize() throws IOException {
        log.info("Service Account key path >>> {}",serviceAccountKeyPath);
//        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
//        Blob blob = storage.get(bucketName, serviceAccountKeyPath);
//        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(blob.getContent());
        Resource resource = resourceLoader.getResource(serviceAccountKeyPath);
        try (InputStream serviceAccount = resource.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    //.setStorageBucket(bucketName)
                    .build();
//
            FirebaseApp.initializeApp(options);
        }catch (Exception e){

            log.info("An exception has occurred while trying to load the Firebase App >>> {}", e.getStackTrace());
        }
    }
}
