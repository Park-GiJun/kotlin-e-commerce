package com.gijun.kotlinecommerce.domain.order.model

enum class OrderStatusEnums(
    val code: String,
    val description: String
) {
    PENDING("PENDING", "주문 대기"),
    PAYMENT_WAITING("PAYMENT_WAITING", "결제 대기"),
    PAID("PAID", "결제 완료"),
    PREPARING("PREPARING", "상품 준비중"),
    SHIPPING("SHIPPING", "배송중"),
    DELIVERED("DELIVERED", "배송 완료"),
    CONFIRMED("CONFIRMED", "구매 확정"),
    CANCELLED("CANCELLED", "주문 취소"),
    REFUND_REQUESTED("REFUND_REQUESTED", "환불 요청"),
    REFUNDED("REFUNDED", "환불 완료"),
    EXCHANGE_REQUESTED("EXCHANGE_REQUESTED", "교환 요청"),
    EXCHANGED("EXCHANGED", "교환 완료")
}