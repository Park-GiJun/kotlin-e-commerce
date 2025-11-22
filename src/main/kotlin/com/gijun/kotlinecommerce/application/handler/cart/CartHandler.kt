package com.gijun.kotlinecommerce.application.handler.cart

import com.gijun.kotlinecommerce.domain.cart.model.CartAction
import com.gijun.kotlinecommerce.application.port.input.cart.CartUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.cart.CartJpaPort
import com.gijun.kotlinecommerce.domain.cart.model.CartModel
import org.springframework.stereotype.Service

@Service
class CartHandler(
    private val cartJpaPort: CartJpaPort
) : CartUseCase {

    override fun updateCart(userId: Long, productId: Long, qty: Int, action: CartAction): List<CartModel> {
        val existingCart = cartJpaPort.getCart(userId)
        val existingItem = existingCart.find { it.productId == productId }

        return when (action) {
            CartAction.ADD -> {
                if (existingItem != null) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity + qty)
                    cartJpaPort.save(updatedItem)
                } else {
                    val newItem = CartModel.create(userId, productId, qty)
                    cartJpaPort.save(newItem)
                }
            }
            CartAction.REMOVE -> {
                if (existingItem != null) {
                    val newQty = existingItem.quantity - qty
                    if (newQty <= 0) {
                        cartJpaPort.delete(existingItem)
                    } else {
                        val updatedItem = existingItem.copy(quantity = newQty)
                        cartJpaPort.save(updatedItem)
                    }
                } else {
                    existingCart
                }
            }
        }
    }

    override fun getCart(userId: Long): List<CartModel> {
        return cartJpaPort.getCart(userId)
    }
}