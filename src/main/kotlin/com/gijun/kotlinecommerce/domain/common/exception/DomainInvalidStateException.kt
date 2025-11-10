package com.gijun.kotlinecommerce.domain.common.exception

open class DomainInvalidStateException(
    message: String = "Invalid state",
    cause: Throwable? = null
) : RuntimeException(message, cause)