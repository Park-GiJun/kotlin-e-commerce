package com.gijun.kotlinecommerce.application.port.input.product

import com.gijun.kotlinecommerce.application.dto.command.product.product.CreateProductCommand
import com.gijun.kotlinecommerce.application.dto.command.product.product.UpdateProductCommand
import com.gijun.kotlinecommerce.application.dto.result.product.GetProductResult
import com.gijun.kotlinecommerce.domain.common.PageRequest
import com.gijun.kotlinecommerce.domain.common.PageResponse
import com.gijun.kotlinecommerce.domain.product.model.ProductModel

interface ProductUseCase {
    fun createProduct(command: CreateProductCommand) : ProductModel
    fun getProductById(id : Long) : ProductModel
    fun getAllProducts(pageRequest: PageRequest) : PageResponse<GetProductResult>
    fun getProductsByCategory(categoryId: Long, pageRequest: PageRequest) : PageResponse<GetProductResult>
    fun deleteProduct(id : Long) : ProductModel
    fun updateProduct(command : UpdateProductCommand) : ProductModel
}