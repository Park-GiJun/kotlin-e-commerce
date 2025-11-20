package com.gijun.kotlinecommerce.application.port.input.product

import com.gijun.kotlinecommerce.application.dto.command.product.productPrice.CreateProductPriceCommand
import com.gijun.kotlinecommerce.application.dto.command.product.productPrice.UpdateProductPriceCommand
import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import java.time.LocalDate

interface ProductPriceUseCase {
    fun createProductPrice(command: CreateProductPriceCommand) : ProductPriceModel
    fun updateProductPrice(command: UpdateProductPriceCommand) : ProductPriceModel
    fun updateAllProductPriceByToDay(today: LocalDate) : List<ProductPriceModel>
    fun getAllProductPrices(): List<ProductPriceModel>
    fun getProductPricesByProductId(productId: Long): List<ProductPriceModel>
    fun getProductPriceById(id: Long): ProductPriceModel
    fun deleteProductPrice(id: Long): ProductPriceModel
}