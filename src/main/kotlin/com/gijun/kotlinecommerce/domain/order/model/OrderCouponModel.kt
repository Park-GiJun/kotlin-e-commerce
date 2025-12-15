package com.gijun.kotlinecommerce.domain.order.model

data class OrderCouponModel(
    val id: Long?,
    val orderId: Long,
    val couponId: Long,
    val couponCode: String,
    val discountPrice: Long
) {
    companion object {
        fun create(
            orderId: Long,
            couponId: Long,
            couponCode: String,
            discountPrice: Long
        ): OrderCouponModel {
            return OrderCouponModel(
                id = null,
                orderId = orderId,
                couponId = couponId,
                couponCode = couponCode,
                discountPrice = discountPrice
            )
        }

        fun of(
            id: Long,
            orderId: Long,
            couponId: Long,
            couponCode: String,
            discountPrice: Long
        ): OrderCouponModel {
            return OrderCouponModel(
                id = id,
                orderId = orderId,
                couponId = couponId,
                couponCode = couponCode,
                discountPrice = discountPrice
            )
        }
    }
}
