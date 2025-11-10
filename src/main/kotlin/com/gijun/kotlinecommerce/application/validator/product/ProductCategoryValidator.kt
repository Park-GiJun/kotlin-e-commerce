package com.gijun.kotlinecommerce.application.validator.product

import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductCategoryJpaPort
import com.gijun.kotlinecommerce.domain.common.validator.CommonValidators
import com.gijun.kotlinecommerce.domain.product.exception.ProductCategoryNotFoundException
import com.gijun.kotlinecommerce.domain.product.exception.ProductValidationException
import com.gijun.kotlinecommerce.domain.product.model.ProductCategoryModel

/**
 * ProductCategory 도메인 Validator (Application Layer)
 */
class ProductCategoryValidator(
    private val productCategoryJpaPort: ProductCategoryJpaPort
) {
    companion object {
        private const val CATEGORY_NAME_MIN_LENGTH = 1
        private const val CATEGORY_NAME_MAX_LENGTH = 100
    }

    /**
     * ProductCategory가 존재하는지 검증하고 반환
     */
    fun validateCategoryExists(categoryId: Long): ProductCategoryModel {
        return productCategoryJpaPort.findById(categoryId)
            ?: throw ProductCategoryNotFoundException(categoryId)
    }

    /**
     * ProductCategory 이름으로 존재하는지 검증하고 반환
     */
    fun validateCategoryExistsByName(name: String): ProductCategoryModel {
        return productCategoryJpaPort.findByCategoryName(name)
            ?: throw ProductCategoryNotFoundException("Product category with name '$name' not found")
    }

    /**
     * ProductCategory 이름 유효성 검증
     */
    fun validateCategoryName(name: String) {
        try {
            CommonValidators.validateNotBlank(name, "Category name")
            CommonValidators.validateLength(name, "Category name", CATEGORY_NAME_MIN_LENGTH, CATEGORY_NAME_MAX_LENGTH)
        } catch (e: Exception) {
            throw ProductValidationException(e.message ?: "Category name validation failed", e)
        }
    }

    /**
     * ProductCategory 생성 시 전체 유효성 검증
     */
    fun validateForCreate(parentId: Long, name: String) {
        validateCategoryName(name)
        CommonValidators.validateNonNegative(parentId, "Parent Category ID")
    }

    /**
     * ProductCategory 업데이트 시 전체 유효성 검증
     */
    fun validateForUpdate(id: Long, parentId: Long, name: String) {
        CommonValidators.validatePositive(id, "Category ID")
        validateCategoryName(name)
        CommonValidators.validateNonNegative(parentId, "Parent Category ID")
    }
}
