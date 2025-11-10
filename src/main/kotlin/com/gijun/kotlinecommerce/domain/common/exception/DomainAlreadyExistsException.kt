package com.gijun.kotlinecommerce.domain.common.exception

open class DomainAlreadyExistsException(
    message: String = "Resource already exists",
    cause: Throwable? = null
) : RuntimeException(message, cause)