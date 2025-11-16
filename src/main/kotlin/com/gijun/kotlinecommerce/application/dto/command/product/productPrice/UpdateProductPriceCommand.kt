package com.gijun.kotlinecommerce.application.dto.command.product.productPrice

import java.math.BigInteger

data class UpdateProductPriceCommand(
    val productPriceId: Long,
    val productId: Long,
    val price: BigInteger,
    val startDate: String,
    val endDate: String
)
