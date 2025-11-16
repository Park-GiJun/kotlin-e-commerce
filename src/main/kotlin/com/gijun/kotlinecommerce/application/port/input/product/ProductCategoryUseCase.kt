package com.gijun.kotlinecommerce.application.port.input.product

import com.gijun.kotlinecommerce.application.dto.command.product.productCategory.CreateProductCategoryCommand
import com.gijun.kotlinecommerce.application.dto.command.product.productCategory.UpdateProductCategoryCommand
import com.gijun.kotlinecommerce.application.dto.result.product.GetProductCategoryHierarchyResult
import com.gijun.kotlinecommerce.domain.product.model.ProductCategoryModel

interface ProductCategoryUseCase {
    fun createProductCategory(command: CreateProductCategoryCommand): ProductCategoryModel
    fun getAllProductCategories(): List<ProductCategoryModel>
    fun getProductCategoryById(categoryId: Long): ProductCategoryModel
    fun updateProductCategory(command: UpdateProductCategoryCommand): ProductCategoryModel
    fun deleteProductCategory(categoryId: Long): ProductCategoryModel
    fun getProductCategoryHierarchies(): List<GetProductCategoryHierarchyResult>
}