package com.gijun.kotlinecommerce.domain.product.exception

import com.gijun.kotlinecommerce.domain.common.exception.DomainValidationException

class ProductValidationException(
    message: String = "Product validation failed",
    cause: Throwable? = null
) : DomainValidationException(message, cause)