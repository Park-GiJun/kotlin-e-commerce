package com.gijun.kotlinecommerce.application.port.output.persistence.product

import com.gijun.kotlinecommerce.domain.product.model.ProductStockModel

interface ProductStockJpaPort {
    fun save(productStockModel: ProductStockModel): ProductStockModel
    fun findById(id: Long): ProductStockModel?
    fun findByProductId(productId: Long): ProductStockModel?
    fun findAll(): List<ProductStockModel>
    fun update(productStockModel: ProductStockModel): ProductStockModel
    fun delete(productStockModel: ProductStockModel): ProductStockModel
    fun isGreaterThanZero(productId: Long): Boolean
}