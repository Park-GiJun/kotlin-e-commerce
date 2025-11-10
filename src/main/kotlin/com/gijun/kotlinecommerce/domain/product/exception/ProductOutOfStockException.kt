package com.gijun.kotlinecommerce.domain.product.exception

import com.gijun.kotlinecommerce.domain.common.exception.DomainConflictException

class ProductOutOfStockException(
    message: String = "Product is out of stock",
    cause: Throwable? = null
) : DomainConflictException(message, cause) {
    constructor(productId: Long, requestedQuantity: Int, availableQuantity: Int) : this(
        "Product $productId is out of stock. Requested: $requestedQuantity, Available: $availableQuantity"
    )
}