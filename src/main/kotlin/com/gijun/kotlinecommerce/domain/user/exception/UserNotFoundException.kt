package com.gijun.kotlinecommerce.domain.user.exception

import com.gijun.kotlinecommerce.domain.common.exception.DomainNotFoundException

class UserNotFoundException(
    message: String = "User not found",
    cause: Throwable? = null
) : DomainNotFoundException(message, cause) {
    constructor(userId: Long) : this("User with id $userId not found", null)
    constructor(email: String) : this("User with email $email not found", null)
}
