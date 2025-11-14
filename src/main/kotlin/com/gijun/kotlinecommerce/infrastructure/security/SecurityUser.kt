package com.gijun.kotlinecommerce.infrastructure.security

import com.gijun.kotlinecommerce.domain.user.model.UserModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SecurityUser(
    private val userModel: UserModel
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(userModel.role.getAuthority()))
    }

    override fun getPassword(): String {
        return userModel.password
    }

    override fun getUsername(): String {
        return userModel.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun getUserModel(): UserModel {
        return userModel
    }
}
