package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository

import com.gijun.kotlinecommerce.domain.product.model.ReviewStats
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductReviewJpaEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.projection.ReviewStatsProjection
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class ProductReviewJdslRepository(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext
) {

    fun getReviewStatsByProductId(productId: Long): ReviewStats {
        val countQuery = jpql {
            select(
                count(entity(ProductReviewJpaEntity::class))
            ).from(
                entity(ProductReviewJpaEntity::class)
            ).where(
                path(ProductReviewJpaEntity::productId).equal(productId)
            )
        }

        val avgQuery = jpql {
            select(
                avg(path(ProductReviewJpaEntity::rating))
            ).from(
                entity(ProductReviewJpaEntity::class)
            ).where(
                path(ProductReviewJpaEntity::productId).equal(productId)
            )
        }

        val count = entityManager.createQuery(countQuery, jpqlRenderContext).singleResult?.toInt() ?: 0
        val avgRating = entityManager.createQuery(avgQuery, jpqlRenderContext).singleResult ?: 0.0

        return ReviewStats(count, avgRating)
    }

    fun getReviewStatsByProductIds(productIds: List<Long>): Map<Long, ReviewStats> {
        if (productIds.isEmpty()) return emptyMap()

        val query = jpql {
            selectNew<ReviewStatsProjection>(
                path(ProductReviewJpaEntity::productId),
                count(entity(ProductReviewJpaEntity::class)),
                avg(path(ProductReviewJpaEntity::rating))
            ).from(
                entity(ProductReviewJpaEntity::class)
            ).where(
                path(ProductReviewJpaEntity::productId).`in`(productIds)
            ).groupBy(
                path(ProductReviewJpaEntity::productId)
            )
        }

        return entityManager.createQuery(query, jpqlRenderContext).resultList
            .associate { it.productId to it.toDomain() }
    }
}
