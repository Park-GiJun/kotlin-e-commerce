package com.gijun.kotlinecommerce.application.dto.command.product.product

data class UpdateProductCommand(
    val id: Long,
    val categoryId: Long,
    val name: String
)