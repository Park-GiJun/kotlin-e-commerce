package com.gijun.kotlinecommerce.application.dto.command.product.product

data class CreateProductCommand(
    val categoryId: Long,
    val name: String
)