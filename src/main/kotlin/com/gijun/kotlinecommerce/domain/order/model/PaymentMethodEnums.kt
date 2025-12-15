package com.gijun.kotlinecommerce.domain.order.model

enum class PaymentMethodEnums(
    val type: String,
    val description: String
) {
    CARD("CARD", "카드 결제"),
    CASH("CASH", "현금 결제"),
    BANK("BANK", "무통장 입금"),
    PAY("PAY", "가상 결제")
}