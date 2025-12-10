package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.adapter

import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductReviewJpaPort
import com.gijun.kotlinecommerce.domain.product.model.ReviewStats
import com.gijun.kotlinecommerce.domain.product.model.ProductReviewModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductReviewJpaEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.ProductReviewJdslRepository
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.ProductReviewJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ProductReviewJpaPortAdapter(
    private val productReviewJpaRepository: ProductReviewJpaRepository,
    private val productReviewJdslRepository: ProductReviewJdslRepository
) : ProductReviewJpaPort {
    override fun save(productReviewModel: ProductReviewModel): ProductReviewModel {
        return productReviewJpaRepository.save(ProductReviewJpaEntity.fromDomain(productReviewModel)).toDomainModel()
    }

    override fun isExistByProductIdAndUserId(productId: Long, userId: Long): Boolean {
        return productReviewJpaRepository.existsByProductIdAndReviewerId(productId, userId)
    }

    override fun getProductReviewByProductId(productId: Long): List<ProductReviewModel> {
        return productReviewJpaRepository.findByProductIdOrderByCreatedAtDesc(productId)
            .map(ProductReviewJpaEntity::toDomainModel)
    }

    override fun getReviewStatsByProductId(productId: Long): ReviewStats {
        return productReviewJdslRepository.getReviewStatsByProductId(productId)
    }

    override fun getReviewStatsByProductIds(productIds: List<Long>): Map<Long, ReviewStats> {
        return productReviewJdslRepository.getReviewStatsByProductIds(productIds)
    }
}