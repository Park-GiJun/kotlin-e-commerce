package com.gijun.kotlinecommerce.domain.order.model

import java.time.LocalDate

data class OrderModel(
    val id: Long?,
    val userId: Long,
    val orderNo: String,
    val orderDate: LocalDate,
    val totalPrice: Long,
    val totalDiscountPrice: Long,
    val shippingAddress: String,
    val deliveryFee: Long,
    val orderStatus: OrderStatusEnums
) {
    companion object {
        fun create(
            userId: Long,
            orderNo: String,
            orderDate: LocalDate,
            totalPrice: Long,
            totalDiscountPrice: Long,
            shippingAddress: String,
            deliveryFee: Long,
            orderStatus: OrderStatusEnums = OrderStatusEnums.PENDING
        ): OrderModel {
            return OrderModel(
                id = null,
                userId = userId,
                orderNo = orderNo,
                orderDate = orderDate,
                totalPrice = totalPrice,
                totalDiscountPrice = totalDiscountPrice,
                shippingAddress = shippingAddress,
                deliveryFee = deliveryFee,
                orderStatus = orderStatus
            )
        }

        fun of(
            id: Long,
            userId: Long,
            orderNo: String,
            orderDate: LocalDate,
            totalPrice: Long,
            totalDiscountPrice: Long,
            shippingAddress: String,
            deliveryFee: Long,
            orderStatus: OrderStatusEnums
        ): OrderModel {
            return OrderModel(
                id = id,
                userId = userId,
                orderNo = orderNo,
                orderDate = orderDate,
                totalPrice = totalPrice,
                totalDiscountPrice = totalDiscountPrice,
                shippingAddress = shippingAddress,
                deliveryFee = deliveryFee,
                orderStatus = orderStatus
            )
        }
    }
}
