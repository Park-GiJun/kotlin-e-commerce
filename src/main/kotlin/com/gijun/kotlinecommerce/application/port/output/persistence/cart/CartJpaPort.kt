package com.gijun.kotlinecommerce.application.port.output.persistence.cart

import com.gijun.kotlinecommerce.domain.cart.model.CartModel

interface CartJpaPort {
    fun addProductToCart(cart : CartModel) : List<CartModel>
    fun removeProductFromCart(cart: CartModel) : List<CartModel>
    fun getCart(userId: Long) : List<CartModel>
    fun deleteCart(userId: Long) : Boolean
}