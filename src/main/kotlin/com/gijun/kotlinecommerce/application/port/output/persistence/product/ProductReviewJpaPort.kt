package com.gijun.kotlinecommerce.application.port.output.persistence.product

import com.gijun.kotlinecommerce.domain.product.model.ProductReviewModel
import com.gijun.kotlinecommerce.domain.product.model.ReviewStats

interface ProductReviewJpaPort {
    fun save(productReviewModel: ProductReviewModel): ProductReviewModel
    fun isExistByProductIdAndUserId(productId: Long, userId: Long) : Boolean
    fun getProductReviewByProductId(productId: Long) : List<ProductReviewModel>
    fun getReviewStatsByProductId(productId: Long): ReviewStats
    fun getReviewStatsByProductIds(productIds: List<Long>): Map<Long, ReviewStats>
}

