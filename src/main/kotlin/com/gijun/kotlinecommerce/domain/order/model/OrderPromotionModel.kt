package com.gijun.kotlinecommerce.domain.order.model

data class OrderPromotionModel(
    val id: Long?,
    val orderId: Long,
    val promotionId: Long,
    val discountPrice: Long
) {
    companion object {
        fun create(
            orderId: Long,
            promotionId: Long,
            discountPrice: Long
        ): OrderPromotionModel {
            return OrderPromotionModel(
                id = null,
                orderId = orderId,
                promotionId = promotionId,
                discountPrice = discountPrice
            )
        }

        fun of(
            id: Long,
            orderId: Long,
            promotionId: Long,
            discountPrice: Long
        ): OrderPromotionModel {
            return OrderPromotionModel(
                id = id,
                orderId = orderId,
                promotionId = promotionId,
                discountPrice = discountPrice
            )
        }
    }
}
