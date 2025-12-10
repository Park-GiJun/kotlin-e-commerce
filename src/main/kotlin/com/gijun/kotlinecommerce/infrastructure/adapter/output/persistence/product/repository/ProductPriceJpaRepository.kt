package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductPriceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ProductPriceJpaRepository : JpaRepository<ProductPriceJpaEntity, Long> {
    fun findByEndDate(endDate: LocalDate): List<ProductPriceJpaEntity>
    fun findByProductId(productId: Long): List<ProductPriceJpaEntity>
}