package com.gijun.kotlinecommerce.application.port.output.persistence.product

import com.gijun.kotlinecommerce.domain.common.PageRequest
import com.gijun.kotlinecommerce.domain.product.model.ProductModel

interface ProductJpaPort {
    fun save(productModel: ProductModel): ProductModel
    fun update(productModel: ProductModel): ProductModel
    fun findAll(): List<ProductModel>
    fun findAllWithPaging(pageRequest: PageRequest): Pair<List<ProductModel>, Long>
    fun findByCategoryWithPaging(categoryId: Long, pageRequest: PageRequest): Pair<List<ProductModel>, Long>
    fun findByCategory(categoryId: Long): List<ProductModel>
    fun findById(id: Long): ProductModel?
    fun findByProductName(name: String): ProductModel?
    fun delete(productModel: ProductModel) : ProductModel
}