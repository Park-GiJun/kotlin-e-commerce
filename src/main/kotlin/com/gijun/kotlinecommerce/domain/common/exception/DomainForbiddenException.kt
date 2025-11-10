package com.gijun.kotlinecommerce.domain.common.exception

open class DomainForbiddenException(
    message: String = "Access forbidden",
    cause: Throwable? = null
) : RuntimeException(message, cause)