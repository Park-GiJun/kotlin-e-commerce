package com.gijun.kotlinecommerce.domain.coupon.model

enum class CouponStatusEnums(
    val code: String,
    val description: String
) {
    ACTIVE("ACTIVE", "활성"),
    INACTIVE("INACTIVE", "비활성"),
    EXPIRED("EXPIRED", "만료"),
    EXHAUSTED("EXHAUSTED", "소진")
}
