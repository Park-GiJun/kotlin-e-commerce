package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductStockJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductStockJpaRepository : JpaRepository<ProductStockJpaEntity, Long> {
    fun findByProductId(productId: Long): ProductStockJpaEntity?

    fun existsByProductIdAndQuantityGreaterThan(productId: Long, quantity: Int): Boolean
}