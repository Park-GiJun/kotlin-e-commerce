package com.gijun.kotlinecommerce.domain.product.exception

import com.gijun.kotlinecommerce.domain.common.exception.DomainNotFoundException

class ProductNotFoundException(
    message: String = "Product not found",
    cause: Throwable? = null
) : DomainNotFoundException(message, cause) {
    constructor(productId: Long) : this("Product with id $productId not found")
}