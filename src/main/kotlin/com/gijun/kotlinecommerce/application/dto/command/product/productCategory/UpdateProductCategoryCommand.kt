package com.gijun.kotlinecommerce.application.dto.command.product.productCategory

data class UpdateProductCategoryCommand (
    val id: Long,
    val name: String,
    val parentId: Long? = null
)