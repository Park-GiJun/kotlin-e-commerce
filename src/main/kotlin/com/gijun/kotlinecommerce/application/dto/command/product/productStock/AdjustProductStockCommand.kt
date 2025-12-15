package com.gijun.kotlinecommerce.application.dto.command.product.productStock

import com.gijun.kotlinecommerce.domain.common.eunms.ActionEnums

data class AdjustProductStockCommand(
    val stockId: Long? = null,
    val productId: Long,
    val quantity: Int,
    val actionEnums: ActionEnums
)
