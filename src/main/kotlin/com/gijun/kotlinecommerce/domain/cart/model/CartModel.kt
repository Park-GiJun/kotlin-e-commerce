package com.gijun.kotlinecommerce.domain.cart.model

data class CartModel(
   val id: Long? = null,
   val userId: Long,
   val productId: Long,
   val quantity: Int
) {
    companion object {
        fun create(userId: Long, productId: Long, quantity: Int): CartModel {
            return CartModel(
                id = null,
                userId = userId,
                productId = productId,
                quantity = quantity
            )
        }

        fun of(id: Long, userId: Long, productId: Long, quantity: Int): CartModel {
            return CartModel(
                id = id,
                userId = userId,
                productId = productId,
                quantity = quantity
            )
        }
    }
}
