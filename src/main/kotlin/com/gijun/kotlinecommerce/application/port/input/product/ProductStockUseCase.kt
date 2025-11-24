package com.gijun.kotlinecommerce.application.port.input.product

import com.gijun.kotlinecommerce.application.dto.command.product.productStock.AdjustProductStockCommand
import com.gijun.kotlinecommerce.domain.product.model.ProductStockModel

interface ProductStockUseCase {
    fun adjustProductStock(command : AdjustProductStockCommand) : ProductStockModel
    fun getAllProductStock() : List<ProductStockModel>
}