package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.user.request

data class LoginRequest(
    val email: String,
    val password: String
)
