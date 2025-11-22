package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.cart.repository

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.cart.entity.CartJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CartJpaRepository : JpaRepository<CartJpaEntity, Long> {
    fun findByUserId(userId: Long): List<CartJpaEntity>
}