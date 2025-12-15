package com.gijun.kotlinecommerce.domain.order.model

enum class PaymentStatusEnums(
    val code: String,
    val description: String
) {
    PENDING("PENDING", "결제 대기"),
    COMPLETED("COMPLETED", "결제 완료"),
    FAILED("FAILED", "결제 실패"),
    CANCELLED("CANCELLED", "결제 취소"),
    REFUNDED("REFUNDED", "환불 완료"),
    PARTIAL_REFUNDED("PARTIAL_REFUNDED", "부분 환불")
}
