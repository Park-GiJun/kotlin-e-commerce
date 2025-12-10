package com.gijun.kotlinecommerce.application.handler.cart

import com.gijun.kotlinecommerce.application.annotation.DistributeLock
import com.gijun.kotlinecommerce.domain.common.eunms.Action
import com.gijun.kotlinecommerce.application.port.input.cart.CartUseCase
import com.gijun.kotlinecommerce.application.port.output.persistence.cart.CartJpaPort
import com.gijun.kotlinecommerce.domain.cart.model.CartModel
import com.gijun.kotlinecommerce.domain.lock.model.DistributedLockType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CartHandler(
    private val cartJpaPort: CartJpaPort
) : CartUseCase {

    @Transactional
    @DistributeLock(type = DistributedLockType.CART, key = "#userId")
    override fun updateCart(userId: Long, productId: Long, qty: Int, action: Action): List<CartModel> {
        val existingCart = cartJpaPort.getCart(userId)
        val existingItem = existingCart.find { it.productId == productId }

        return when (action) {
            Action.ADD -> {
                if (existingItem != null) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity + qty)
                    cartJpaPort.save(updatedItem)
                } else {
                    val newItem = CartModel.create(userId, productId, qty)
                    cartJpaPort.save(newItem)
                }
            }
            Action.REMOVE -> {
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