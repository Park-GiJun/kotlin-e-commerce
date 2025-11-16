package com.gijun.kotlinecommerce.application.port.output.persistence.product

import com.gijun.kotlinecommerce.domain.common.PageRequest
import com.gijun.kotlinecommerce.domain.product.model.ProductCategoryModel

interface ProductCategoryJpaPort {
    fun save(productCategory: ProductCategoryModel) : ProductCategoryModel
    fun findAll() : List<ProductCategoryModel>
    fun findAllWithPaging(pageRequest: PageRequest): Pair<List<ProductCategoryModel>, Long>
    fun findById(id: Long) : ProductCategoryModel?
    fun findByCategoryName(name: String) : ProductCategoryModel?
    fun update(productCategory: ProductCategoryModel) : ProductCategoryModel
    fun delete(id: Long) : ProductCategoryModel
    fun findProductHierarchies() : List<ProductCategoryModel>
}