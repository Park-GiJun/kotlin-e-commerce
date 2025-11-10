package com.gijun.kotlinecommerce.application.dto.command.product.productCategory

data class CreateProductCategoryCommand(
    val name: String,
    val parentId: Long? = null
)
