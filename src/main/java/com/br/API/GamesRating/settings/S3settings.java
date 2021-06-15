package com.br.API.GamesRating.settings;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3settings {

  @Value("${AWS_ACCESS_KEY_ID}")
  private String awsId;

  @Value("${AWS_SECRET_ACCESS_KEY}")
  private String awsKey;

  @Value("${s3.region}")
  private String region;

  @Bean
  public AmazonS3 s3client() {
    var awsCred = new BasicAWSCredentials(awsId, awsKey);
    var s3client =
        AmazonS3ClientBuilder.standard()
            .withRegion(Regions.fromName(region))
            .withCredentials(new AWSStaticCredentialsProvider(awsCred))
            .build();
    return s3client;
  }
}
