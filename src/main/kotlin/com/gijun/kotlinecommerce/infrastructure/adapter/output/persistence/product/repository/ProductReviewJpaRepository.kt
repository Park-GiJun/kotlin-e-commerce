package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductReviewJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductReviewJpaRepository : JpaRepository<ProductReviewJpaEntity, Long> {
    fun existsByProductIdAndReviewerId(productId: Long, reviewerId: Long) : Boolean
    fun findByProductId(productId: Long) : List<ProductReviewJpaEntity>
}