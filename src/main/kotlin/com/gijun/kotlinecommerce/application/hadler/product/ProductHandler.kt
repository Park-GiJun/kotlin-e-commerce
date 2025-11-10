package com.gijun.kotlinecommerce.application.hadler.product

import com.gijun.kotlinecommerce.application.dto.command.product.product.CreateProductCommand
import com.gijun.kotlinecommerce.application.dto.command.product.product.UpdateProductCommand
import com.gijun.kotlinecommerce.application.port.input.product.ProductUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductCategoryJpaPort
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductJpaPort
import com.gijun.kotlinecommerce.application.validator.product.ProductCategoryValidator
import com.gijun.kotlinecommerce.application.validator.product.ProductValidator
import com.gijun.kotlinecommerce.domain.product.model.ProductModel

class ProductHandler(
    private val productJpaPort: ProductJpaPort,
    private val productCategoryJpaPort: ProductCategoryJpaPort
) : ProductUseCase {
    private val productValidator = ProductValidator(productJpaPort)
    private val categoryValidator = ProductCategoryValidator(productCategoryJpaPort)
    override fun createProduct(command: CreateProductCommand): ProductModel {
        productValidator.validateForCreate(command.categoryId, command.name)
        categoryValidator.validateCategoryExists(command.categoryId)

        val newProduct = ProductModel.create(command.categoryId, command.name)
        return productJpaPort.save(newProduct)
    }

    override fun getProductById(id: Long): ProductModel =
        productValidator.validateProductExists(id)

    override fun getAllProducts(): List<ProductModel> = productJpaPort.findAll()

    override fun deleteProduct(id: Long): ProductModel {
        val product = productValidator.validateProductExists(id)
        return productJpaPort.delete(product)
    }

    override fun updateProduct(command: UpdateProductCommand): ProductModel {
        productValidator.validateForUpdate(command.id, command.categoryId, command.name)
        categoryValidator.validateCategoryExists(command.categoryId)
        productValidator.validateProductExists(command.id)

        val product = ProductModel.of(command.id, command.categoryId, command.name)
        return productJpaPort.update(product)
    }
}