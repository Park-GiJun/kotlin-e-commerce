package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.product.request

data class CreateReviewRequest(
    val reviewerId: Long,
    val rating: Int,
    val comment: String
)