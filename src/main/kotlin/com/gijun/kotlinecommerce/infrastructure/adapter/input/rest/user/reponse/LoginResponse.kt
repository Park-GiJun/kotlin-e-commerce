package com.gijun.kotlinecommerce.infrastructure.adapter.input.rest.user.reponse

import com.gijun.kotlinecommerce.domain.user.model.UserModel
import com.gijun.kotlinecommerce.domain.user.model.UserRole

data class LoginResponse(
    val token: String,
    val user: UserResponseDto
) {
    companion object {
        fun of(token: String, user: UserModel): LoginResponse {
            return LoginResponse(
                token = token,
                user = UserResponseDto(
                    id = user.id,
                    email = user.email,
                    name = user.name,
                    role = user.role
                )
            )
        }
    }
}

data class UserResponseDto(
    val id: Long?,
    val email: String,
    val name: String,
    val role: UserRole
)
