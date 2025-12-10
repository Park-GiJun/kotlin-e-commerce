package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductPriceJpaEntity
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ProductPriceJdslRepository(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) {
    fun findCurrentPricesByProductIds(
        productIds: List<Long>,
        currentDate: LocalDate
    ): List<ProductPriceJpaEntity> {
        if (productIds.isEmpty()) return emptyList()

        val query = jpql {
            select(
                entity(ProductPriceJpaEntity::class)
            ).from(
                entity(ProductPriceJpaEntity::class)
            ).where(
                and(
                    path(ProductPriceJpaEntity::productId).`in`(productIds),
                    path(ProductPriceJpaEntity::startDate).le(currentDate),
                    path(ProductPriceJpaEntity::endDate).ge(currentDate)
                )
            )
        }

        return entityManager.createQuery(query, jpqlRenderContext).resultList
    }

    fun findProductPriceByProductId(productId: Long, currentDate: LocalDate): ProductPriceJpaEntity? {
        val query = jpql {
            select(
                entity(ProductPriceJpaEntity::class)
            ).from(
                entity(ProductPriceJpaEntity::class)
            ).where(
                and(
                    path(ProductPriceJpaEntity::productId).equal(productId),
                    path(ProductPriceJpaEntity::startDate).le(currentDate),
                    path(ProductPriceJpaEntity::endDate).ge(currentDate)
                )
            )
        }
        return entityManager.createQuery(query, jpqlRenderContext).resultList.firstOrNull()
    }

    fun findProductPrice(currentDate: LocalDate): List<ProductPriceJpaEntity> {
        val query = jpql {
            select(
                entity(ProductPriceJpaEntity::class)
            ).from(
                entity(ProductPriceJpaEntity::class)
            ).where(
                and(
                    path(ProductPriceJpaEntity::startDate).le(currentDate),
                    path(ProductPriceJpaEntity::endDate).ge(currentDate)
                )
            )
        }
        return entityManager.createQuery(query, jpqlRenderContext).resultList
    }
}