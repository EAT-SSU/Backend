package com.ssu.eatssu.global.enums

enum class ErrorMessages(val message: String) {
    NOT_FOUND_MENU("해당 메뉴를 찾을 수 없습니다."),
    NOT_EXIST_CATEGORY("해당 카테고리가 존재하지 않습니다."),
    NOT_EXIST_RESTAURANT("해당 식당이 존재하지 않습니다."),
    NOT_EXIST_TYPE("해당 타입이 존재하지 않습니다."),
    NOT_EXIST_TIMEPART("해당 시간대가 존재하지 않습니다."),
    NOT_EXIST_USER("해당 유저가 존재하지 않습니다."),
    NOT_EXISTS_MENU("해당 메뉴가 존재하지 않습니다."),
    NOT_EXISTS_MEAL("해당 식사가 존재하지 않습니다."),

    ALREADY_EXIST_NICKNAME("이미 존재하는 닉네임입니다."),
    ALREADY_EXIST_EMAIL("이미 존재하는 이메일입니다."),

    INVALID_IDENTITY_TOKEN("유효하지 않은 토큰입니다."),
    INVALID_RESTAURANT("유효하지 않은 식당입니다."), ;
}