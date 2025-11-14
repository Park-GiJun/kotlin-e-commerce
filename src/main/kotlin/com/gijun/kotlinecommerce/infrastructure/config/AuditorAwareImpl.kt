package com.gijun.kotlinecommerce.infrastructure.config

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuditorAwareImpl : AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication == null || !authentication.isAuthenticated) {
            return Optional.of("system")
        }

        return when (val principal = authentication.principal) {
            is UserDetails -> Optional.of(principal.username)
            is String -> Optional.of(principal)
            else -> Optional.of("system")
        }
    }
}
