package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.product

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.product.ProductPriceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductPriceJpaRepository : JpaRepository<ProductPriceJpaEntity, Long> {
    fun findByProductIdInAndEndDate(productIds: List<Long>, endDate: String): List<ProductPriceJpaEntity>
    fun findByEndDate(endDate: String): List<ProductPriceJpaEntity>
    fun findByProductId(productId: Long): List<ProductPriceJpaEntity>
}