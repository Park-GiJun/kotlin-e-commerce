package com.gijun.kotlinecommerce.domain.user.model

enum class PointTypeEnums(
    val code: String,
    val description: String
) {
    EARN_PURCHASE("EARN_PURCHASE", "구매 적립"),
    EARN_REVIEW("EARN_REVIEW", "리뷰 적립"),
    EARN_EVENT("EARN_EVENT", "이벤트 적립"),
    EARN_SIGNUP("EARN_SIGNUP", "회원가입 적립"),
    EARN_ADMIN("EARN_ADMIN", "관리자 지급"),
    USE_ORDER("USE_ORDER", "주문 사용"),
    CANCEL_ORDER("CANCEL_ORDER", "주문 취소 반환"),
    EXPIRE("EXPIRE", "소멸")
}
