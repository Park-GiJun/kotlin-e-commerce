package com.gijun.kotlinecommerce.domain.product.exception

import com.gijun.kotlinecommerce.domain.common.exception.DomainConflictException

class ProductReviewConflictException(
    message: String = "Product Review already exists",
    cause: Throwable? = null
) : DomainConflictException() {
    constructor(productId: Long) : this("Product Review for product $productId already exists", null)
}