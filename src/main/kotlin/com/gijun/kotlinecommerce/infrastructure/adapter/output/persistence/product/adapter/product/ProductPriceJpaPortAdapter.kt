package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.adapter.product

import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductPriceJpaPort
import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.product.ProductPriceJpaEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.product.ProductPriceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProductPriceJpaPortAdapter(
    private val productPriceJpaRepository: ProductPriceJpaRepository
) : ProductPriceJpaPort {

    override fun findCurrentPricesByProductIds(productIds: List<Long>): Map<Long, ProductPriceModel> {
        if (productIds.isEmpty()) return emptyMap()

        return productPriceJpaRepository
            .findByProductIdInAndEndDate(productIds, CURRENT_PRICE_END_DATE)
            .map { it.toDomainModel() }
            .associateBy { it.productId }
    }

    override fun findAllCurrentPrices(): List<ProductPriceModel> {
        return productPriceJpaRepository
            .findByEndDate(CURRENT_PRICE_END_DATE)
            .map { it.toDomainModel() }
    }

    override fun findByProductId(productId: Long): List<ProductPriceModel> {
        return productPriceJpaRepository
            .findByProductId(productId)
            .map { it.toDomainModel() }
    }

    override fun save(productPriceModel: ProductPriceModel): ProductPriceModel {
        val entity = ProductPriceJpaEntity.fromDomain(productPriceModel)
        return productPriceJpaRepository.save(entity).toDomainModel()
    }

    override fun findById(id: Long): ProductPriceModel? {
        return productPriceJpaRepository.findByIdOrNull(id)?.toDomainModel()
    }

    override fun update(productPriceModel: ProductPriceModel): ProductPriceModel {
        val entity = ProductPriceJpaEntity.fromDomain(productPriceModel)
        return productPriceJpaRepository.save(entity).toDomainModel()
    }

    override fun delete(productPriceModel: ProductPriceModel): ProductPriceModel {
        productPriceModel.id?.let { productPriceJpaRepository.deleteById(it) }
        return productPriceModel
    }

    companion object {
        private const val CURRENT_PRICE_END_DATE = "9999-12-31"
    }
}