package com.gijun.kotlinecommerce.domain.product.model

import com.gijun.kotlinecommerce.domain.common.eunms.ActionEnums

data class ProductStockModel(
    val id: Long?,
    val productId: Long,
    val quantity: Int
) {
    fun update(actionEnums: ActionEnums, quantity: Int): ProductStockModel {
        val newQuantity = when (actionEnums) {
            ActionEnums.ADD -> this.quantity + quantity
            ActionEnums.REMOVE -> this.quantity - quantity
        }
        return this.copy(quantity = newQuantity)
    }

    companion object {
        fun create(productId: Long, quantity: Int): ProductStockModel {
            return ProductStockModel(null, productId, quantity)
        }

        fun of(id: Long, productId: Long, quantity: Int): ProductStockModel {
            return ProductStockModel(id, productId, quantity)
        }
    }
}
