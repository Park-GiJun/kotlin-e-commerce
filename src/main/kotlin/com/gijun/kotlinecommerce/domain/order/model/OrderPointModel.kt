package com.gijun.kotlinecommerce.domain.order.model

data class OrderPointModel(
    val id: Long?,
    val orderId: Long,
    val point: Int,
    val discountPrice: Long
) {
    companion object {
        fun create(
            orderId: Long,
            point: Int,
            discountPrice: Long
        ): OrderPointModel {
            return OrderPointModel(
                id = null,
                orderId = orderId,
                point = point,
                discountPrice = discountPrice
            )
        }

        fun of(
            id: Long,
            orderId: Long,
            point: Int,
            discountPrice: Long
        ): OrderPointModel {
            return OrderPointModel(
                id = id,
                orderId = orderId,
                point = point,
                discountPrice = discountPrice
            )
        }
    }
}
