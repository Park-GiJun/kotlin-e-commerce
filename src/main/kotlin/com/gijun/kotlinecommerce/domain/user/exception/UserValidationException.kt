package com.gijun.kotlinecommerce.domain.user.exception

import com.gijun.kotlinecommerce.domain.common.exception.DomainValidationException

class UserValidationException(
    message: String = "User validation failed",
    cause: Throwable? = null
) : DomainValidationException(message, cause)
