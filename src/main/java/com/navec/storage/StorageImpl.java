package com.navec.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.InputStream;

public class StorageImpl implements Storage {
    private final AmazonS3 s3client;

    public StorageImpl() {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIA2OLDO4BPHBYC6XWF",
                "InXWMTxRAxmoLCoeiKHioGbnnIr7x9mdGAEi9vkV"
        );

        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_2)
                .build();
    }

    @Override
    public void putFile(String filename, InputStream stream) {
        this.s3client.putObject("navecstorage", filename, stream, null);
    }
}
