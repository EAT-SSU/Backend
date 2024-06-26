package com.ssu.eatssu.domain.slack.entity

enum class SlackChannel(val koreanName: String) {
    REPORT_CHANNEL("#신고"),
    ADDMENU_CHANNEL("#메뉴_추가"),
    ERROR_CHANNEL("#장애"),
    USER_INQUIRY_CHANNEL("#유저-문의");
}