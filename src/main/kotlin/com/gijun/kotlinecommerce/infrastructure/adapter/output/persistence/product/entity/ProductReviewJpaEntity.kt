package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity

import com.gijun.kotlinecommerce.domain.product.model.ProductReviewModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "product_review")
class ProductReviewJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, name = "product_id")
    var productId: Long,

    @Column(nullable = false, name = "rating")
    var rating: Int,

    @Column(nullable = false, name = "comment")
    var comment: String,

    @Column(nullable = false, name = "reviewer_name")
    var reviewerName: String,

    @Column(nullable = false, name = "reviewer_id")
    var reviewerId: Long,

    @Column(nullable = false, name = "review_date")
    var reviewDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false, name = "is_modified")
    var isModified: Boolean = false
) : BaseEntity() {

    fun toDomainModel() : ProductReviewModel {
     return ProductReviewModel(
         id = id,
         productId = productId,
         rating = rating,
         comment = comment,
         reviewerName = reviewerName,
         reviewerId = reviewerId,
         reviewDate = reviewDate,
         isModified = isModified
     )
    }

    companion object {
        fun fromDomain(model: ProductReviewModel): ProductReviewJpaEntity {
            return ProductReviewJpaEntity(
                id = model.id,
                productId = model.productId,
                rating = model.rating,
                comment = model.comment,
                reviewerName = model.reviewerName,
                reviewerId = model.reviewerId,
                reviewDate = model.reviewDate,
                isModified = model.isModified
            )
        }
    }
}