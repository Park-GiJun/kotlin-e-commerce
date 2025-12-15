package com.gijun.kotlinecommerce.domain.order.model

import java.time.LocalDateTime

data class OrderPaymentModel(
    val id: Long?,
    val orderId: Long,
    val paymentMethod: PaymentMethodEnums,
    val paymentAmount: Long,
    val paymentStatus: PaymentStatusEnums,
    val transactionId: String?,
    val paidAt: LocalDateTime?
) {
    companion object {
        fun create(
            orderId: Long,
            paymentMethod: PaymentMethodEnums,
            paymentAmount: Long,
            paymentStatus: PaymentStatusEnums = PaymentStatusEnums.PENDING,
            transactionId: String? = null,
            paidAt: LocalDateTime? = null
        ): OrderPaymentModel {
            return OrderPaymentModel(
                id = null,
                orderId = orderId,
                paymentMethod = paymentMethod,
                paymentAmount = paymentAmount,
                paymentStatus = paymentStatus,
                transactionId = transactionId,
                paidAt = paidAt
            )
        }

        fun of(
            id: Long,
            orderId: Long,
            paymentMethod: PaymentMethodEnums,
            paymentAmount: Long,
            paymentStatus: PaymentStatusEnums,
            transactionId: String?,
            paidAt: LocalDateTime?
        ): OrderPaymentModel {
            return OrderPaymentModel(
                id = id,
                orderId = orderId,
                paymentMethod = paymentMethod,
                paymentAmount = paymentAmount,
                paymentStatus = paymentStatus,
                transactionId = transactionId,
                paidAt = paidAt
            )
        }
    }
}
