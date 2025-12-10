package com.gijun.kotlinecommerce.application.dto.result.product.product

import java.math.BigInteger
import java.time.LocalDate

data class GetProductResult(
    val productId : Long,
    val productName : String,
    val isOnSale: Boolean,
    val largeClassId: Long,
    val largeClassName: String,
    val mediumClassId: Long?,
    val mediumClassName: String?,
    val smallClassId: Long?,
    val smallClassName: String?,
    val categoryDepth: Int,
    val productPrice: BigInteger,
    val priceStartDate: LocalDate,
    val priceEndDate: LocalDate,
    val reviewCount: Int = 0,
    val averageRating: Double = 0.0,
    val reviewList: List<ProductReviewResult>? = null
)