package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.adapter

import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductStockJpaPort
import com.gijun.kotlinecommerce.domain.product.model.ProductStockModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductStockJpaEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.ProductStockJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ProductStockJpaPortAdapter(
    private val productStockJpaRepository: ProductStockJpaRepository
) : ProductStockJpaPort {

    override fun save(productStockModel: ProductStockModel): ProductStockModel {
        return productStockJpaRepository.save(ProductStockJpaEntity.fromDomain(productStockModel)).toDomain()
    }

    override fun findById(id: Long): ProductStockModel? {
        return productStockJpaRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun findByProductId(productId: Long): ProductStockModel? {
        return productStockJpaRepository.findByProductId(productId)?.toDomain()
    }

    override fun findAll(): List<ProductStockModel> {
        return productStockJpaRepository.findAll().map { it.toDomain() }
    }

    override fun update(productStockModel: ProductStockModel): ProductStockModel {
        val entity = ProductStockJpaEntity.fromDomain(productStockModel).update(productStockModel.quantity)
        return productStockJpaRepository.save(entity).toDomain()
    }

    override fun delete(productStockModel: ProductStockModel): ProductStockModel {
        productStockModel.id?.let { productStockJpaRepository.deleteById(it) }
        return productStockModel
    }

    override fun isGreaterThanZero(productId: Long): Boolean {
        return productStockJpaRepository.existsByProductIdAndQuantityGreaterThan(productId, 0)
    }
}