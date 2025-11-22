package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.cart.request

import com.gijun.kotlinecommerce.domain.cart.model.CartAction

data class CartUpdateRequest(
    val productId: Long,
    val qty: Int,
    val action: CartAction
)