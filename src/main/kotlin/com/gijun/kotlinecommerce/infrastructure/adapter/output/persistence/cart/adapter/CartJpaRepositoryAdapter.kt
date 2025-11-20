package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.cart.adapter

import com.gijun.kotlinecommerce.application.port.output.persistence.cart.CartJpaPort
import com.gijun.kotlinecommerce.domain.cart.model.CartModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.cart.repository.CartJpaRepository

class CartJpaRepositoryAdapter(
    private val cartJpaRepository: CartJpaRepository
) : CartJpaPort {

    override fun addProductToCart(cart: CartModel): List<CartModel> {
        TODO("Not yet implemented")
    }

    override fun removeProductFromCart(cart: CartModel): List<CartModel> {
        TODO("Not yet implemented")
    }

    override fun getCart(userId: Long): List<CartModel> {
        TODO("Not yet implemented")
    }

    override fun deleteCart(userId: Long): Boolean {
        TODO("Not yet implemented")
    }

}