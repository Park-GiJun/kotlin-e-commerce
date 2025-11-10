package com.gijun.kotlinecommerce.application.hadler.product

import com.gijun.kotlinecommerce.application.dto.command.product.productCategory.CreateProductCategoryCommand
import com.gijun.kotlinecommerce.application.dto.command.product.productCategory.UpdateProductCategoryCommand
import com.gijun.kotlinecommerce.application.port.input.product.ProductCategoryUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductCategoryJpaPort
import com.gijun.kotlinecommerce.application.validator.product.ProductCategoryValidator
import com.gijun.kotlinecommerce.domain.product.model.ProductCategoryModel

class ProductCategoryHandler(
    private val productCategoryJpaPort: ProductCategoryJpaPort
) : ProductCategoryUseCase {
    private val categoryValidator = ProductCategoryValidator(productCategoryJpaPort)

    override fun createProductCategory(command: CreateProductCategoryCommand): ProductCategoryModel {
        val parentId = command.parentId ?: 0
        categoryValidator.validateForCreate(parentId, command.name)

        // parentId가 0이 아니면 부모 카테고리가 존재하는지 검증
        if (parentId != 0L) {
            categoryValidator.validateCategoryExists(parentId)
        }

        val newCategory = ProductCategoryModel.create(command.name, parentId)
        return productCategoryJpaPort.save(newCategory)
    }

    override fun getAllProductCategories(): List<ProductCategoryModel> {
        return productCategoryJpaPort.findAll()
    }

    override fun getProductCategoryById(categoryId: Long): ProductCategoryModel {
        return categoryValidator.validateCategoryExists(categoryId)
    }

    override fun updateProductCategory(command: UpdateProductCategoryCommand): ProductCategoryModel {
        val parentId = command.parentId ?: 0
        categoryValidator.validateForUpdate(command.id, parentId, command.name)
        categoryValidator.validateCategoryExists(command.id)

        // parentId가 0이 아니면 부모 카테고리가 존재하는지 검증
        if (parentId != 0L) {
            categoryValidator.validateCategoryExists(parentId)
        }

        // 자기 자신을 부모로 설정하는 것 방지
        if (command.id == parentId) {
            throw IllegalArgumentException("Category cannot be its own parent")
        }

        val category = ProductCategoryModel.of(command.id, command.name, parentId)
        return productCategoryJpaPort.update(category)
    }

    override fun deleteProductCategory(categoryId: Long): ProductCategoryModel {
        categoryValidator.validateCategoryExists(categoryId)
        return productCategoryJpaPort.delete(categoryId)
    }
}