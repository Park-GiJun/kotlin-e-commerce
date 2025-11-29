package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductImageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductImageJpaRepository : JpaRepository<ProductImageJpaEntity, Long> {
}