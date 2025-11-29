package com.gijun.kotlinecommerce.application.dto.command.product.productImage

data class UploadProductImageCommand(
    val productId: Long,
    val order: Int,
    val isMain: Boolean,
    val imageUrl: String
)
