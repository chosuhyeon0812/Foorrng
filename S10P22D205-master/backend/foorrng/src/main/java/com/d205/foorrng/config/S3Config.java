package com.d205.foorrng.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Value("${cloud.aws.credentials.access-key}") // "${cloud.aws.credentials.access-key}"
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key}") // "${cloud.aws.credentials.secret-key}"
    private String secretKey;
    @Value("${cloud.aws.region}")
    private String region;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // amazonS3Client에 사용자 인증 정보, 버켓 리전 설정
    @Bean
    public AmazonS3Client amazonS3Client(){
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
