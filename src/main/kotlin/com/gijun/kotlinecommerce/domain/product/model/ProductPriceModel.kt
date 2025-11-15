package com.gijun.kotlinecommerce.domain.product.model

import java.math.BigDecimal
import java.time.LocalDate

data class ProductPriceModel(
    val id : Long,
    val productId: Long,
    val price: BigDecimal,
    val startDate : LocalDate,
    val endDate : LocalDate
)
