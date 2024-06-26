package com.ssu.eatssu.domain.report.presentation

import com.ssu.eatssu.domain.report.application.ReportCommandService
import com.ssu.eatssu.domain.report.entity.ReportType
import com.ssu.eatssu.domain.report.presentation.dto.CreateReportRequest
import com.ssu.eatssu.domain.slack.application.SlackClient
import com.ssu.eatssu.global.auth.user.entity.CustomUserDetails
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reports")
class ReportController(
    private val reportCommandService: ReportCommandService,
    private val slackClient: SlackClient
) : ReportControllerDocs {

    @GetMapping("/types")
    override fun getReportType(): ResponseEntity<List<ReportType>> {
        TODO("Not yet implemented")
    }

    @PostMapping
    override fun reportReview(
        createReportRequest: CreateReportRequest?,
        customUserDetails: CustomUserDetails?
    ): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

}