package com.gijun.kotlinecommerce.domain.common.exception

open class DomainConflictException(
    message: String = "Resource conflict",
    cause: Throwable? = null
) : RuntimeException(message, cause)