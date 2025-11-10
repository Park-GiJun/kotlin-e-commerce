package com.gijun.kotlinecommerce.application.port.output.persistence.product

import com.gijun.kotlinecommerce.domain.product.model.ProductModel

interface ProductJpaPort {
    fun save(productModel: ProductModel): ProductModel
    fun update(productModel: ProductModel): ProductModel
    fun findAll(): List<ProductModel>
    fun findByCategory(categoryId: Long): List<ProductModel>
    fun findById(id: Long): ProductModel?
    fun findByProductName(name: String): ProductModel?
    fun delete(productModel: ProductModel) : ProductModel
}