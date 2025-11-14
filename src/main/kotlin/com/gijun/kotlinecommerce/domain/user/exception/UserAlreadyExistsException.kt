package com.gijun.kotlinecommerce.domain.user.exception

import com.gijun.kotlinecommerce.domain.common.exception.DomainAlreadyExistsException

class UserAlreadyExistsException(
    message: String = "User already exists",
    cause: Throwable? = null
) : DomainAlreadyExistsException(message, cause) {
    constructor(email: String) : this("User with email $email already exists", null)
}
