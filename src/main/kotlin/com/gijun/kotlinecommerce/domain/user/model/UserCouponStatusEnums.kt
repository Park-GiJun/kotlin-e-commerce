package com.gijun.kotlinecommerce.domain.user.model

enum class UserCouponStatusEnums(
    val code: String,
    val description: String
) {
    AVAILABLE("AVAILABLE", "사용 가능"),
    USED("USED", "사용 완료"),
    EXPIRED("EXPIRED", "만료")
}
