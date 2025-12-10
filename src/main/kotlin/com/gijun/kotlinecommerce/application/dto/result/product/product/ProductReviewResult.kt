package com.gijun.kotlinecommerce.application.dto.result.product.product

data class ProductReviewResult(
    val productId: Long,
    val rating: Int,
    val comment: String,
    val reviewerId: Long,
    val reviewerName: String,
    val reviewDate: String
) {
}