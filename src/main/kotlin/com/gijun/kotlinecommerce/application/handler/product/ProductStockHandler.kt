package com.gijun.kotlinecommerce.application.handler.product

import com.gijun.kotlinecommerce.application.dto.command.product.productStock.AdjustProductStockCommand
import com.gijun.kotlinecommerce.application.port.input.product.ProductStockUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductStockJpaPort
import com.gijun.kotlinecommerce.domain.common.eunms.Action
import com.gijun.kotlinecommerce.domain.product.exception.ProductOutOfStockException
import com.gijun.kotlinecommerce.domain.product.model.ProductStock

class ProductStockHandler(
    private val productStockJpaPort: ProductStockJpaPort
) : ProductStockUseCase {
    override fun adjustProductStock(command: AdjustProductStockCommand): ProductStock {
        val productStock = productStockJpaPort.findByProductId(command.productId)
            ?: ProductStock.create(command.productId, command.quantity)

        when(command.action) {
            Action.ADD -> {
                if (productStock.id == null) {
                    return productStockJpaPort.save(productStock)
                } else {
                    productStock.update(Action.ADD, command.quantity)
                }
            }
            Action.REMOVE -> {
                if(productStock.quantity <= command.quantity) {
                    throw ProductOutOfStockException(command.productId,command.quantity, productStock.quantity)
                } else {
                    productStock.update(Action.REMOVE, command.quantity)
                }
            }
        }
        return productStockJpaPort.save(productStock)
    }

    override fun getAllProductStock(): List<ProductStock> {
        return productStockJpaPort.findAll()
    }
}