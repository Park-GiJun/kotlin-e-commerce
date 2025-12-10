package com.gijun.kotlinecommerce.application.dto.command.product.productReview

data class CreateProductReviewCommand(
    val productId: Long,
    val reviewerId: Long,
    val rating: Int,
    val comment: String
)
