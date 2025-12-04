package com.gijun.kotlinecommerce.application.handler.product

import com.gijun.kotlinecommerce.application.dto.command.product.productCategory.CreateProductCategoryCommand
import com.gijun.kotlinecommerce.application.dto.command.product.productCategory.UpdateProductCategoryCommand
import com.gijun.kotlinecommerce.application.dto.result.product.productCategory.GetProductCategoryHierarchyResult
import com.gijun.kotlinecommerce.application.port.input.product.ProductCategoryUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductCategoryJpaPort
import com.gijun.kotlinecommerce.domain.common.validator.CommonValidators
import com.gijun.kotlinecommerce.domain.product.exception.ProductCategoryNotFoundException
import com.gijun.kotlinecommerce.domain.product.exception.ProductValidationException
import com.gijun.kotlinecommerce.domain.product.model.ProductCategoryModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductCategoryHandler(
    private val productCategoryJpaPort: ProductCategoryJpaPort
) : ProductCategoryUseCase {

    @Transactional
    override fun createProductCategory(command: CreateProductCategoryCommand): ProductCategoryModel {
        val parentId = command.parentId ?: 0
        validateForCreate(parentId, command.name)

        if (parentId != 0L) {
            val parentCategory = validateCategoryExists(parentId)
            validateCategoryDepth(parentCategory)
        }

        val newCategory = ProductCategoryModel.create(command.name, parentId)
        return productCategoryJpaPort.save(newCategory)
    }

    override fun getAllProductCategories(): List<ProductCategoryModel> {
        return productCategoryJpaPort.findAll()
    }

    override fun getProductCategoryById(categoryId: Long): ProductCategoryModel {
        return validateCategoryExists(categoryId)
    }

    @Transactional
    override fun updateProductCategory(command: UpdateProductCategoryCommand): ProductCategoryModel {
        val parentId = command.parentId ?: 0
        validateForUpdate(command.id, parentId, command.name)
        validateCategoryExists(command.id)

        if (parentId != 0L) {
            val parentCategory = validateCategoryExists(parentId)
            validateCategoryDepth(parentCategory)
        }

        if (command.id == parentId) {
            throw IllegalArgumentException("Category cannot be its own parent")
        }

        val category = ProductCategoryModel.of(command.id, command.name, parentId)
        return productCategoryJpaPort.update(category)
    }

    @Transactional
    override fun deleteProductCategory(categoryId: Long): ProductCategoryModel {
        validateCategoryExists(categoryId)
        return productCategoryJpaPort.delete(categoryId)
    }

    override fun getProductCategoryHierarchies(): List<GetProductCategoryHierarchyResult> {
        val allCategories = productCategoryJpaPort.findProductHierarchies()
        val categoryMap = allCategories.associateBy { it.id!! }

        return allCategories
            .filter { it.parentId == 0L }
            .map { buildHierarchy(it, categoryMap, 0) }
    }

    private fun buildHierarchy(
        category: ProductCategoryModel,
        categoryMap: Map<Long, ProductCategoryModel>,
        depth: Int
    ): GetProductCategoryHierarchyResult {
        val result = GetProductCategoryHierarchyResult()
        result.id = category.id!!
        result.name = category.name
        result.depth = depth
        result.parentId = if (category.parentId == 0L) null else category.parentId

        result.children = categoryMap.values
            .filter { it.parentId == category.id }
            .map { buildHierarchy(it, categoryMap, depth + 1) }

        return result
    }

    private fun validateCategoryExists(categoryId: Long): ProductCategoryModel {
        return productCategoryJpaPort.findById(categoryId)
            ?: throw ProductCategoryNotFoundException(categoryId)
    }

    private fun validateCategoryName(name: String) {
        try {
            CommonValidators.validateNotBlank(name, "Category name")
            CommonValidators.validateLength(name, "Category name", CATEGORY_NAME_MIN_LENGTH, CATEGORY_NAME_MAX_LENGTH)
        } catch (e: Exception) {
            throw ProductValidationException(e.message ?: "Category name validation failed", e)
        }
    }

    private fun validateForCreate(parentId: Long, name: String) {
        validateCategoryName(name)
        CommonValidators.validateNonNegative(parentId, "Parent Category ID")
    }

    private fun validateForUpdate(id: Long, parentId: Long, name: String) {
        CommonValidators.validatePositive(id, "Category ID")
        validateCategoryName(name)
        CommonValidators.validateNonNegative(parentId, "Parent Category ID")
    }

    private fun validateCategoryDepth(parentCategory: ProductCategoryModel) {
        // Calculate the depth of the parent category
        val allCategories = productCategoryJpaPort.findAll().associateBy { it.id!! }
        var depth = 1
        var currentCategory: ProductCategoryModel? = parentCategory

        while (currentCategory != null && currentCategory.parentId != 0L) {
            depth++
            currentCategory = allCategories[currentCategory.parentId]
        }

        if (depth >= MAX_CATEGORY_DEPTH) {
            throw ProductValidationException(
                "Category depth cannot exceed $MAX_CATEGORY_DEPTH levels. Current parent is at depth $depth."
            )
        }
    }

    companion object {
        private const val CATEGORY_NAME_MIN_LENGTH = 1
        private const val CATEGORY_NAME_MAX_LENGTH = 100
        private const val MAX_CATEGORY_DEPTH = 3
    }
}