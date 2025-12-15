package com.gijun.kotlinecommerce.domain.promotion.model

enum class PromotionStatusEnums(
    val code: String,
    val description: String
) {
    SCHEDULED("SCHEDULED", "예정"),
    ACTIVE("ACTIVE", "진행중"),
    PAUSED("PAUSED", "일시중지"),
    ENDED("ENDED", "종료"),
    CANCELLED("CANCELLED", "취소")
}
