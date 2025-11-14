package com.gijun.kotlinecommerce.infrastructure.security

import com.gijun.kotlinecommerce.application.port.output.persistence.user.UserJpaPort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userJpaPort: UserJpaPort
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userJpaPort.findByUserId(username)
            ?: throw UsernameNotFoundException("User not found with email: $username")

        return SecurityUser(user)
    }
}
