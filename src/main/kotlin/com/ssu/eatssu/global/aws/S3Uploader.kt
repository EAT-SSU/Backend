package com.ssu.eatssu.global.aws

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class S3Uploader (
    val amazonS3Client: AmazonS3Client,
    val s3Properties: S3Properties
){
    @Throws(IOException::class)
    fun upload(multipartFile: MultipartFile, dirName: String): String {
        val uploadFile = convert(multipartFile)
            .orElseThrow {
                IllegalArgumentException(
                    "MultipartFile -> File로 전환이 실패했습니다."
                )
            }
        return upload(uploadFile, dirName)
    }

    private fun upload(uploadFile: File, dirName: String): String {
        val fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName()
        val uploadImageUrl = putS3(uploadFile, fileName)
        removeNewFile(uploadFile)
        return uploadImageUrl
    }

    private fun putS3(uploadFile: File, fileName: String): String {
        amazonS3Client.putObject(
            PutObjectRequest(s3Properties.s3.bucket, fileName, uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead
            )
        )
        return amazonS3Client.getUrl(s3Properties.s3.bucket, fileName).toString()
    }

    private fun removeNewFile(targetFile: File) {
        if (targetFile.delete()) {
            // TODO : 로그 추가
            println("파일이 삭제되었습니다.")
        } else {
            println("파일이 삭제되지 못했습니다.")
        }
    }

    @Throws(IOException::class)
    private fun convert(file: MultipartFile): Optional<File> {
        val formatedNow = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        val convertFile = File(formatedNow + file.originalFilename)
        if (convertFile.createNewFile()) {
            FileOutputStream(convertFile).use { fos -> fos.write(file.bytes) }
            return Optional.of(convertFile)
        }
        return Optional.empty()
    }

}