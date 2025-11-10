package com.gijun.kotlinecommerce.domain.common.exception

open class DomainUnauthorizedException(
    message: String = "Unauthorized access",
    cause: Throwable? = null
) : RuntimeException(message, cause)