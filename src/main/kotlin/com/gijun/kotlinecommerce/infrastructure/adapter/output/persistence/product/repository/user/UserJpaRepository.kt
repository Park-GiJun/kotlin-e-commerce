package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.user

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.user.UserJpaEntity
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long>, KotlinJdslJpqlExecutor {
    fun findByEmail(email: String): UserJpaEntity?
}
