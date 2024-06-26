package com.ssu.eatssu.global.aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonS3Config(private val s3Properties: S3Properties) {

    @Bean
    fun amazonS3Client(): AmazonS3Client? {
        val awsCreds = BasicAWSCredentials(
            s3Properties.credentials.accessKey,
            s3Properties.credentials.secretKey
        )

        return AmazonS3ClientBuilder
            .standard()
            .withRegion(s3Properties.region.static)
            .withCredentials(AWSStaticCredentialsProvider(awsCreds))
            .build() as AmazonS3Client?
    }
}