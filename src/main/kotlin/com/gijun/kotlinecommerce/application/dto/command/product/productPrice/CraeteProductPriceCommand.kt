package com.gijun.kotlinecommerce.application.dto.command.product.productPrice

import java.math.BigInteger

data class CreateProductPriceCommand(
    val productId: Long,
    val price: BigInteger,
    val startDate: String,
    val endDate: String
)
