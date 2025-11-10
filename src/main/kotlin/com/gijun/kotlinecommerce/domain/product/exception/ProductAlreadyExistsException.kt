package com.gijun.kotlinecommerce.domain.product.exception

import com.gijun.kotlinecommerce.domain.common.exception.DomainAlreadyExistsException

class ProductAlreadyExistsException(
    message: String = "Product already exists",
    cause: Throwable? = null
) : DomainAlreadyExistsException(message, cause) {
    constructor(productName: String) : this("Product with name '$productName' already exists")
}