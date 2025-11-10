package com.gijun.kotlinecommerce.domain.common.exception

open class DomainValidationException(
    message: String = "Validation failed",
    cause: Throwable? = null
) : RuntimeException(message, cause)