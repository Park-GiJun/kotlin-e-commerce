package com.gijun.kotlinecommerce.application.port.input.cart

import com.gijun.kotlinecommerce.domain.common.eunms.ActionEnums
import com.gijun.kotlinecommerce.domain.cart.model.CartModel

interface CartUseCase {
    fun updateCart(userId: Long, productId: Long, qty: Int, actionEnums: ActionEnums): List<CartModel>
    fun getCart(userId: Long): List<CartModel>
}