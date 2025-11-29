package com.gijun.kotlinecommerce.application.dto.command.product.productStock

import com.gijun.kotlinecommerce.domain.common.eunms.Action

data class AdjustProductStockCommand(
    val stockId: Long? = null,
    val productId: Long,
    val quantity: Int,
    val action: Action
)
