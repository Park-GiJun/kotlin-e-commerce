package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.projection

import com.gijun.kotlinecommerce.domain.product.model.ReviewStats

data class ReviewStatsProjection(
    val productId: Long,
    val count: Long,
    val avgRating: Double?
) {
    fun toDomain(): ReviewStats {
        return ReviewStats(
            count = count.toInt(),
            averageRating = avgRating ?: 0.0
        )
    }
}
