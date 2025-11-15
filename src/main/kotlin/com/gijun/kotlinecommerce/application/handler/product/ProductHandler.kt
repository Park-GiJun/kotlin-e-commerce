package com.gijun.kotlinecommerce.application.handler.product

import com.gijun.kotlinecommerce.application.dto.command.product.product.CreateProductCommand
import com.gijun.kotlinecommerce.application.dto.command.product.product.UpdateProductCommand
import com.gijun.kotlinecommerce.application.dto.result.product.GetProductListResult
import com.gijun.kotlinecommerce.application.port.input.product.ProductUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductCategoryJpaPort
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductJpaPort
import com.gijun.kotlinecommerce.domain.common.validator.CommonValidators
import com.gijun.kotlinecommerce.domain.product.exception.ProductCategoryNotFoundException
import com.gijun.kotlinecommerce.domain.product.exception.ProductNotFoundException
import com.gijun.kotlinecommerce.domain.product.exception.ProductValidationException
import com.gijun.kotlinecommerce.domain.product.model.ProductCategoryModel
import com.gijun.kotlinecommerce.domain.product.model.ProductModel
import org.springframework.stereotype.Service

@Service
class ProductHandler(
    private val productJpaPort: ProductJpaPort,
    private val productCategoryJpaPort: ProductCategoryJpaPort
) : ProductUseCase {

    override fun createProduct(command: CreateProductCommand): ProductModel {
        validateForCreate(command.categoryId, command.name)
        validateCategoryExists(command.categoryId)

        val newProduct = ProductModel.create(command.categoryId, command.name)
        return productJpaPort.save(newProduct)
    }

    override fun getProductById(id: Long): ProductModel =
        validateProductExists(id)

    override fun getAllProducts(): List<GetProductListResult> {
      return
    }

    override fun deleteProduct(id: Long): ProductModel {
        val product = validateProductExists(id)
        return productJpaPort.delete(product)
    }

    override fun updateProduct(command: UpdateProductCommand): ProductModel {
        validateForUpdate(command.id, command.categoryId, command.name)
        validateCategoryExists(command.categoryId)
        validateProductExists(command.id)

        val product = ProductModel.of(command.id, command.categoryId, command.name)
        return productJpaPort.update(product)
    }

    private fun validateProductExists(productId: Long): ProductModel {
        return productJpaPort.findById(productId)
            ?: throw ProductNotFoundException(productId)
    }

    private fun validateProductName(name: String) {
        try {
            CommonValidators.validateNotBlank(name, "Product name")
            CommonValidators.validateLength(name, "Product name", PRODUCT_NAME_MIN_LENGTH, PRODUCT_NAME_MAX_LENGTH)
        } catch (e: Exception) {
            throw ProductValidationException(e.message ?: "Product name validation failed", e)
        }
    }

    private fun validateForCreate(categoryId: Long, name: String) {
        validateProductName(name)
        CommonValidators.validatePositive(categoryId, "Category ID")
    }

    private fun validateForUpdate(id: Long, categoryId: Long, name: String) {
        CommonValidators.validatePositive(id, "Product ID")
        validateProductName(name)
        CommonValidators.validatePositive(categoryId, "Category ID")
    }

    private fun validateCategoryExists(categoryId: Long): ProductCategoryModel {
        return productCategoryJpaPort.findById(categoryId)
            ?: throw ProductCategoryNotFoundException(categoryId)
    }

    companion object {
        private const val PRODUCT_NAME_MIN_LENGTH = 1
        private const val PRODUCT_NAME_MAX_LENGTH = 200
    }
}