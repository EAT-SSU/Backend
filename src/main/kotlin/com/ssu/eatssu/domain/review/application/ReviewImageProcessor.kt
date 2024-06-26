package com.ssu.eatssu.domain.review.application

import com.ssu.eatssu.domain.review.entity.Review
import com.ssu.eatssu.global.aws.S3Uploader
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class ReviewImageProcessor(
    val s3Uploader: S3Uploader
) {
    fun processImage(review: Review, image: MultipartFile): String {
        return s3Uploader.upload(image, "reviewImg")
    }
}