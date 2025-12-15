package com.gijun.kotlinecommerce.domain.coupon.model

enum class CouponTypeEnums(
    val code: String,
    val description: String
) {
    FIXED_AMOUNT("FIXED_AMOUNT", "정액 할인"),
    PERCENTAGE("PERCENTAGE", "정률 할인"),
    FREE_SHIPPING("FREE_SHIPPING", "무료 배송")
}
