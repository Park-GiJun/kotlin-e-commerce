package com.gijun.kotlinecommerce.domain.promotion.model

enum class PromotionTypeEnums(
    val code: String,
    val description: String
) {
    FIXED_AMOUNT("FIXED_AMOUNT", "정액 할인"),
    PERCENTAGE("PERCENTAGE", "정률 할인"),
    BUY_ONE_GET_ONE("BUY_ONE_GET_ONE", "1+1"),
    FREE_SHIPPING("FREE_SHIPPING", "무료 배송"),
    BUNDLE("BUNDLE", "묶음 할인")
}
