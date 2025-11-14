package com.gijun.kotlinecommerce.domain.user.model

enum class UserRole(val description: String) {
    USER("일반 사용자"),
    ADMIN("관리자");

    fun getAuthority(): String {
        return "ROLE_$name"
    }
}
