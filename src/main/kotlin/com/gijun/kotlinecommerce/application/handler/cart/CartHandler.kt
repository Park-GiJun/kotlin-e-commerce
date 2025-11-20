package com.gijun.kotlinecommerce.application.handler.cart

import com.gijun.kotlinecommerce.application.port.input.cart.CartUseCase
import com.gijun.kotlinecommerce.domain.cart.model.CartModel

class CartHandler(

) : CartUseCase {

    override fun addProductToCart(
        userId: Long,
        productId: Long,
        qty: Int
    ): List<CartModel> {
        TODO("Not yet implemented")
    }

    override fun removeProductFromCart(
        userId: Long,
        productId: Long,
        qty: Int
    ): List<CartModel> {
        TODO("Not yet implemented")
    }

    override fun getCart(userId: Long): List<CartModel> {
        TODO("Not yet implemented")
    }
}