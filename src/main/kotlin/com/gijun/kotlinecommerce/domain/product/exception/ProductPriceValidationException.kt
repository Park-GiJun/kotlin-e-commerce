package com.gijun.kotlinecommerce.domain.product.exception

class ProductPriceValidationException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)
