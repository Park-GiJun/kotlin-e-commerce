package com.gijun.kotlinecommerce.application.dto.command.user

data class RegisterUserCommand(
    val email: String,
    val name: String,
    val password: String
)
