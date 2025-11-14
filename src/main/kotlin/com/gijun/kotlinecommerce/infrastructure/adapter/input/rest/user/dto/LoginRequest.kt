package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.user.dto

data class LoginRequest(
    val email: String,
    val password: String
)
