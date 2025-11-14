package com.gijun.kotlinecommerce.domain.user.model

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.user.UserJpaEntity

data class UserModel(
    val id: Long?,
    val email: String,
    val name: String,
    val password: String
) {
    companion object {
        fun create(email: String, name: String, password: String): UserModel {
            return UserModel(null, email, name, password)
        }

        fun of(id: Long, email: String, name: String, password: String): UserModel {
            return UserModel(id, email, name, password)
        }
    }
}
