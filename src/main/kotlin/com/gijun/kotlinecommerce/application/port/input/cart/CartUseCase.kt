package com.gijun.kotlinecommerce.application.port.input.cart

import com.gijun.kotlinecommerce.domain.common.eunms.Action
import com.gijun.kotlinecommerce.domain.cart.model.CartModel

interface CartUseCase {
    fun updateCart(userId: Long, productId: Long, qty: Int, action: Action): List<CartModel>
    fun getCart(userId: Long): List<CartModel>
}