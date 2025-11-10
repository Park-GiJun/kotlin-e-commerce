package com.gijun.kotlinecommerce.application.validator.product

import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductJpaPort
import com.gijun.kotlinecommerce.domain.common.validator.CommonValidators
import com.gijun.kotlinecommerce.domain.product.exception.ProductNotFoundException
import com.gijun.kotlinecommerce.domain.product.exception.ProductValidationException
import com.gijun.kotlinecommerce.domain.product.model.ProductModel

/**
 * Product 도메인 Validator (Application Layer)
 */
class ProductValidator(
    private val productJpaPort: ProductJpaPort
) {
    companion object {
        private const val PRODUCT_NAME_MIN_LENGTH = 1
        private const val PRODUCT_NAME_MAX_LENGTH = 200
    }

    /**
     * Product가 존재하는지 검증하고 반환
     */
    fun validateProductExists(productId: Long): ProductModel {
        return productJpaPort.findById(productId)
            ?: throw ProductNotFoundException(productId)
    }

    /**
     * Product 이름으로 존재하는지 검증하고 반환
     */
    fun validateProductExistsByName(name: String): ProductModel {
        return productJpaPort.findByProductName(name)
            ?: throw ProductNotFoundException("Product with name '$name' not found")
    }

    /**
     * Product 이름 유효성 검증
     */
    fun validateProductName(name: String) {
        try {
            CommonValidators.validateNotBlank(name, "Product name")
            CommonValidators.validateLength(name, "Product name", PRODUCT_NAME_MIN_LENGTH, PRODUCT_NAME_MAX_LENGTH)
        } catch (e: Exception) {
            throw ProductValidationException(e.message ?: "Product name validation failed", e)
        }
    }

    /**
     * Product 생성 시 전체 유효성 검증
     */
    fun validateForCreate(categoryId: Long, name: String) {
        validateProductName(name)
        CommonValidators.validatePositive(categoryId, "Category ID")
    }

    /**
     * Product 업데이트 시 전체 유효성 검증
     */
    fun validateForUpdate(id: Long, categoryId: Long, name: String) {
        CommonValidators.validatePositive(id, "Product ID")
        validateProductName(name)
        CommonValidators.validatePositive(categoryId, "Category ID")
    }
}
