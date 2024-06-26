package com.ssu.eatssu.global.aws

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "cloud.aws")
data class S3Properties(
    var credentials: Credentials = Credentials(),
    var region: Region = Region(),
    var s3: S3 = S3()
) {
    data class Credentials(
        var accessKey: String = "",
        var secretKey: String = ""
    )

    data class Region(
        var static: String = ""
    )


    data class S3(
        var bucket: String = ""
    )
}
