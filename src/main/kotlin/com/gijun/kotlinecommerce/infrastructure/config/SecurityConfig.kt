package com.gijun.kotlinecommerce.infrastructure.config

import com.gijun.kotlinecommerce.infrastructure.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers(
                        "/",
                        "/login.html",
                        "/register.html",
                        "/api/users/register",
                        "/api/users/login",
                        "/api/products/search",
                        "/js/**",
                        "/css/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-config/**",
                        "/webjars/**"
                    ).permitAll()
                    // Product READ - All authenticated users
                    .requestMatchers(HttpMethod.GET, "/api/products/**").authenticated()
                    // Product CUD - ADMIN only
                    .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")
                    // Product Category READ - All authenticated users
                    .requestMatchers(HttpMethod.GET, "/api/product-categories/**").authenticated()
                    // Product Category CUD - ADMIN only
                    .requestMatchers(HttpMethod.POST, "/api/product-categories/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/product-categories/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/product-categories/**").hasRole("ADMIN")
                    // All other requests - authenticated
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .httpBasic { it.disable() }
            .formLogin { it.disable() }

        return http.build()
    }
}
