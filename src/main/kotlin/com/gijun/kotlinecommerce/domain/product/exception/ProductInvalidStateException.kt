package com.gijun.kotlinecommerce.domain.product.exception

import com.gijun.kotlinecommerce.domain.common.exception.DomainInvalidStateException

class ProductInvalidStateException(
    message: String = "Product is in invalid state",
    cause: Throwable? = null
) : DomainInvalidStateException(message, cause)