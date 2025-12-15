package com.gijun.kotlinecommerce.application.handler.product

import com.gijun.kotlinecommerce.application.annotation.DistributeLock
import com.gijun.kotlinecommerce.application.dto.command.product.productStock.AdjustProductStockCommand
import com.gijun.kotlinecommerce.application.port.input.product.ProductStockUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductStockJpaPort
import com.gijun.kotlinecommerce.domain.common.eunms.ActionEnums
import com.gijun.kotlinecommerce.domain.lock.model.DistributedLockTypeEnums
import com.gijun.kotlinecommerce.domain.product.exception.ProductOutOfStockException
import com.gijun.kotlinecommerce.domain.product.model.ProductStockModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductStockHandler(
    private val productStockJpaPort: ProductStockJpaPort
) : ProductStockUseCase {

    @Transactional
    @DistributeLock(type = DistributedLockTypeEnums.STOCK, key = "#command.productId")
    override fun adjustProductStock(command: AdjustProductStockCommand): ProductStockModel {
        val productStockModel = productStockJpaPort.findByProductId(command.productId)
            ?: ProductStockModel.create(command.productId, command.quantity)

        val updatedModel = when(command.actionEnums) {
            ActionEnums.ADD -> {
                if (productStockModel.id == null) {
                    return productStockJpaPort.save(productStockModel)
                } else {
                    productStockModel.update(ActionEnums.ADD, command.quantity)
                }
            }
            ActionEnums.REMOVE -> {
                if(productStockModel.quantity < command.quantity) {
                    throw ProductOutOfStockException(command.productId, command.quantity, productStockModel.quantity)
                } else {
                    productStockModel.update(ActionEnums.REMOVE, command.quantity)
                }
            }
        }
        return productStockJpaPort.save(updatedModel)
    }

    override fun getAllProductStock(): List<ProductStockModel> {
        return productStockJpaPort.findAll()
    }
}