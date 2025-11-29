package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.adapter

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.ProductImageJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ProductImageJpaPortAdapter(
    private val productImageJpaRepository: ProductImageJpaRepository
) {
}