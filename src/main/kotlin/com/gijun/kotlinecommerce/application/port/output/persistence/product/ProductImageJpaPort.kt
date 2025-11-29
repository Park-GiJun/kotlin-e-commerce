package com.gijun.kotlinecommerce.application.port.output.persistence.product

import com.gijun.kotlinecommerce.domain.product.model.ProductImageModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductImageJpaEntity

interface ProductImageJpaPort {
    fun save(productImage: ProductImageModel): ProductImageModel
    fun findById(id: Long): ProductImageModel?
    fun findByProductId(productId: Long): List<ProductImageModel>
    fun findByProductIdAndOrder(productId: Long, order: Int): ProductImageModel?
}