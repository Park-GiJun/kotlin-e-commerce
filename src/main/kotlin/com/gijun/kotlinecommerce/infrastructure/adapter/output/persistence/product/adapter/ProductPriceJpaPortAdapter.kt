package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.adapter

import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductPriceJpaPort
import com.gijun.kotlinecommerce.domain.product.model.ProductPriceModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductPriceJpaEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.ProductPriceJdslRepository
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.ProductPriceJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ProductPriceJpaPortAdapter(
    private val productPriceJpaRepository: ProductPriceJpaRepository,
    private val productPriceJdslRepository: ProductPriceJdslRepository
) : ProductPriceJpaPort {

    override fun findCurrentPricesByProductIds(productIds: List<Long>): List<ProductPriceModel> {
        if (productIds.isEmpty()) return emptyList()

        return productPriceJdslRepository
            .findCurrentPricesByProductIds(productIds, LocalDate.now())
            .map { it.toDomain() }
    }

    override fun findAllCurrentPrices(): List<ProductPriceModel> {
        return productPriceJdslRepository
            .findProductPrice(LocalDate.now())
            .map { it.toDomain() }
    }

    override fun findByProductId(productId: Long): List<ProductPriceModel> {
        return productPriceJpaRepository
            .findByProductId(productId)
            .map { it.toDomain() }
    }

    override fun save(productPriceModel: ProductPriceModel): ProductPriceModel {
        val entity = ProductPriceJpaEntity.fromDomain(productPriceModel)
        return productPriceJpaRepository.save(entity).toDomain()
    }

    override fun findById(id: Long): ProductPriceModel? {
        return productPriceJpaRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun update(productPriceModel: ProductPriceModel): ProductPriceModel {
        val entity = ProductPriceJpaEntity.fromDomain(productPriceModel)
        return productPriceJpaRepository.save(entity).toDomain()
    }

    override fun delete(productPriceModel: ProductPriceModel): ProductPriceModel {
        productPriceModel.id?.let { productPriceJpaRepository.deleteById(it) }
        return productPriceModel
    }
}