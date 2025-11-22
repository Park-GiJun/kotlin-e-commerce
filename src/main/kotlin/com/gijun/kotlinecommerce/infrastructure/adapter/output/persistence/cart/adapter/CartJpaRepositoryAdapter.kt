package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.cart.adapter

import com.gijun.kotlinecommerce.application.port.output.persistence.cart.CartJpaPort
import com.gijun.kotlinecommerce.domain.cart.model.CartModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.cart.entity.CartJpaEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.cart.repository.CartJpaRepository
import org.springframework.stereotype.Repository

@Repository
class CartJpaRepositoryAdapter(
    private val cartJpaRepository: CartJpaRepository
) : CartJpaPort {

    override fun save(cart: CartModel): List<CartModel> {
        cartJpaRepository.save(CartJpaEntity.fromDomain(cart))
        return getCart(cart.userId)
    }

    override fun delete(cart: CartModel): List<CartModel> {
        cart.id?.let { cartJpaRepository.deleteById(it) }
        return getCart(cart.userId)
    }

    override fun getCart(userId: Long): List<CartModel> {
        return cartJpaRepository.findByUserId(userId)
            .map { it.toDomain() }
            .toList()
    }

    override fun deleteCart(userId: Long): Boolean {
        TODO("Not yet implemented")
    }

}