package com.gijun.kotlinecommerce.application.dto.command.user

import com.gijun.kotlinecommerce.domain.user.model.UserRole

data class RegisterUserCommand(
    val email: String,
    val name: String,
    val password: String,
    val role: UserRole = UserRole.USER
)
