package com.gijun.kotlinecommerce.domain.product.model

import java.time.LocalDateTime

data class ProductReviewModel(
    val id: Long?,
    val productId: Long,
    val rating: Int,
    val comment: String,
    val reviewerName: String,
    val reviewerId: Long,
    val reviewDate: LocalDateTime,
    val isModified: Boolean = false
) {
    companion object{
        fun create(productId: Long, rating: Int, comment: String, reviewerName: String, reviewerId: Long): ProductReviewModel {
            return ProductReviewModel(null, productId, rating, comment, reviewerName, reviewerId, LocalDateTime.now())
        }

        fun of(id: Long, productId: Long, rating: Int, comment: String, reviewerName: String, reviewerId: Long, reviewDate: LocalDateTime, isModified: Boolean): ProductReviewModel {
            return ProductReviewModel(id, productId, rating, comment, reviewerName, reviewerId, reviewDate, isModified)
        }
    }
}
