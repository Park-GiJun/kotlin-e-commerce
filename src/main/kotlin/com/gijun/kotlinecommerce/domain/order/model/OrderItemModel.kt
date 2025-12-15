package com.gijun.kotlinecommerce.domain.order.model

data class OrderItemModel(
    val id: Long?,
    val orderId: Long,
    val productId: Long,
    val productName: String,
    val price: Long,
    val salePrice: Long,
    val discountPrice: Long,
    val quantity: Int
) {
    companion object {
        fun create(
            orderId: Long,
            productId: Long,
            productName: String,
            price: Long,
            salePrice: Long,
            discountPrice: Long,
            quantity: Int
        ): OrderItemModel {
            return OrderItemModel(
                id = null,
                orderId = orderId,
                productId = productId,
                productName = productName,
                price = price,
                salePrice = salePrice,
                discountPrice = discountPrice,
                quantity = quantity
            )
        }

        fun of(
            id: Long,
            orderId: Long,
            productId: Long,
            productName: String,
            price: Long,
            salePrice: Long,
            discountPrice: Long,
            quantity: Int
        ): OrderItemModel {
            return OrderItemModel(
                id = id,
                orderId = orderId,
                productId = productId,
                productName = productName,
                price = price,
                salePrice = salePrice,
                discountPrice = discountPrice,
                quantity = quantity
            )
        }
    }
}
