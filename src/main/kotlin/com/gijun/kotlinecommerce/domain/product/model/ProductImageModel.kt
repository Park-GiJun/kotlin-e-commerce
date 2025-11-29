package com.gijun.kotlinecommerce.domain.product.model

data class ProductImageModel(
    val id: Long?,
    val productId: Long,
    val order: Int,
    val isMain: Boolean,
    val url: String
)
