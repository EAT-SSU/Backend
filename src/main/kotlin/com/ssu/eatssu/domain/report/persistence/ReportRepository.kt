package com.ssu.eatssu.domain.report.persistence

import com.ssu.eatssu.domain.report.entity.Report
import org.springframework.data.jpa.repository.JpaRepository

interface ReportRepository : JpaRepository<Report, Long> {
}