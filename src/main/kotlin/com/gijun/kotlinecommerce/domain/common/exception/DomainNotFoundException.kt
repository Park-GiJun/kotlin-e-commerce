package com.gijun.kotlinecommerce.domain.common.exception

open class DomainNotFoundException(
    message: String = "Resource not found",
    cause: Throwable? = null
) : RuntimeException(message, cause)