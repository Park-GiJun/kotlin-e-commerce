package com.gijun.kotlinecommerce.application.dto.result.product.product

import java.math.BigInteger
import java.time.LocalDate

data class GetProductResult(
    val productId : Long,
    val productName : String,
    val largeClassId: Long,
    val largeClassNAme: String,
    val mediumClassId: Long?,
    val mediumClassName: String?,
    val smallClassId: Long?,
    val smallClassName: String?,
    val categoryDepth: Int,
    val productPrice: BigInteger,
    val priceStartDate: LocalDate,
    val priceEndDate: LocalDate
)