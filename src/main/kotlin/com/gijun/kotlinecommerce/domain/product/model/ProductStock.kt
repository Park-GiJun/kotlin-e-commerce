package com.gijun.kotlinecommerce.domain.product.model

import com.gijun.kotlinecommerce.domain.common.eunms.Action

data class ProductStock(
    val id: Long?,
    val productId: Long,
    val quantity: Int
) {
    fun update(action: Action, quantity: Int): ProductStock {
        when (action) {
            Action.ADD -> {
                this.quantity + quantity
            }
            Action.REMOVE -> {
                this.quantity - quantity
            }
        }
        return this
    }

    companion object {
        fun create(productId: Long, quantity: Int): ProductStock {
            return ProductStock(null, productId, quantity)
        }

        fun of(id: Long, productId: Long, quantity: Int): ProductStock {
            return ProductStock(id, productId, quantity)
        }
    }
}
