package com.gijun.kotlinecommerce.application.port.input.product

import com.gijun.kotlinecommerce.application.dto.command.product.productStock.AdjustProductStockCommand
import com.gijun.kotlinecommerce.domain.product.model.ProductStock

interface ProductStockUseCase {
    fun adjustProductStock(command : AdjustProductStockCommand) : ProductStock
    fun getAllProductStock() : List<ProductStock>
}