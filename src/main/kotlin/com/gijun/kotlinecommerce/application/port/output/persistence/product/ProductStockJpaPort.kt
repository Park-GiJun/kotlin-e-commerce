package com.gijun.kotlinecommerce.application.port.output.persistence.product

import com.gijun.kotlinecommerce.domain.product.model.ProductStock

interface ProductStockJpaPort {
    fun save(productStock: ProductStock): ProductStock
    fun findById(id: Long): ProductStock?
    fun findByProductId(productId: Long): ProductStock?
    fun findAll(): List<ProductStock>
    fun update(productStock: ProductStock): ProductStock
    fun delete(productStock: ProductStock): ProductStock
}