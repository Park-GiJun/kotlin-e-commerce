package com.gijun.kotlinecommerce.application.port.input.cart

import com.gijun.kotlinecommerce.domain.cart.model.CartModel

interface CartUseCase {
    fun addProductToCart(userId: Long, productId: Long, qty: Int) : List<CartModel>
    fun removeProductFromCart(userId: Long, productId: Long, qty: Int) : List<CartModel>
    fun getCart(userId: Long) : List<CartModel>
}