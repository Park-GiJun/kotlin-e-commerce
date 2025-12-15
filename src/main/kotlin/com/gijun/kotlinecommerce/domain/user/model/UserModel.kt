package com.gijun.kotlinecommerce.domain.user.model

data class UserModel(
    val id: Long?,
    val email: String,
    val name: String,
    val password: String,
    val role: UserRoleEnums = UserRoleEnums.USER
) {
    companion object {
        fun create(email: String, name: String, password: String, role: UserRoleEnums = UserRoleEnums.USER): UserModel {
            return UserModel(null, email, name, password, role)
        }

        fun of(id: Long, email: String, name: String, password: String, role: UserRoleEnums = UserRoleEnums.USER): UserModel {
            return UserModel(id, email, name, password, role)
        }
    }
}
