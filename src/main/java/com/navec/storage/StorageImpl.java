package com.navec.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;

public class StorageImpl implements Storage {
    private AmazonS3 s3client;

    @Value("${config.storage_key}")
    private String accessKey;

    @Value("${config.storage_priv_key}")
    private String privateKey;

    private AmazonS3 getClient() {
        if(this.s3client == null) {
            AWSCredentials credentials = new BasicAWSCredentials(
                    accessKey,
                    privateKey
            );

            this.s3client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.EU_WEST_2)
                    .build();
        }

        return this.s3client;
    }

    @Override
    public void putFile(String filename, InputStream stream) {
        String bucketName = "navecstorage";
        this.getClient().putObject(bucketName, filename, stream, null);
    }
}
