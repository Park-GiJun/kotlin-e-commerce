package com.gijun.kotlinecommerce.application.port.input.product

import com.gijun.kotlinecommerce.application.dto.command.product.product.CreateProductCommand
import com.gijun.kotlinecommerce.application.dto.command.product.product.UpdateProductCommand
import com.gijun.kotlinecommerce.domain.product.model.ProductModel

interface ProductUseCase {
    fun createProduct(command: CreateProductCommand) : ProductModel
    fun getProductById(id : Long) : ProductModel
    fun getAllProducts() : List<ProductModel>
    fun deleteProduct(id : Long) : ProductModel
    fun updateProduct(command : UpdateProductCommand) : ProductModel
}