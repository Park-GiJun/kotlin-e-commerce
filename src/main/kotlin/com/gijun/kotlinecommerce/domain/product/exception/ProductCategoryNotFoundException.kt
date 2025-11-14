package com.gijun.kotlinecommerce.domain.product.exception

import com.gijun.kotlinecommerce.domain.common.exception.DomainNotFoundException

class ProductCategoryNotFoundException(
    message: String = "Product Category not found",
    cause: Throwable? = null
) : DomainNotFoundException(message, cause) {
    constructor(productId: Long) : this("Product Category with id $productId not found", null)
}