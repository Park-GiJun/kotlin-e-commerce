package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.cart.request

import com.gijun.kotlinecommerce.domain.common.eunms.ActionEnums

data class CartUpdateRequest(
    val productId: Long,
    val qty: Int,
    val actionEnums: ActionEnums
)