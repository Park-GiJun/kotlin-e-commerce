package com.gijun.kotlinecommerce.domain.promotion.model

import java.time.LocalDateTime

data class PromotionModel(
    val id: Long?,
    val promotionName: String,
    val promotionType: PromotionTypeEnums,
    val discountValue: Long,
    val minOrderAmount: Long,
    val maxDiscountAmount: Long?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val status: PromotionStatusEnums,
    val description: String?
) {
    companion object {
        fun create(
            promotionName: String,
            promotionType: PromotionTypeEnums,
            discountValue: Long,
            minOrderAmount: Long = 0,
            maxDiscountAmount: Long? = null,
            startAt: LocalDateTime,
            endAt: LocalDateTime,
            status: PromotionStatusEnums = PromotionStatusEnums.ACTIVE,
            description: String? = null
        ): PromotionModel {
            return PromotionModel(
                id = null,
                promotionName = promotionName,
                promotionType = promotionType,
                discountValue = discountValue,
                minOrderAmount = minOrderAmount,
                maxDiscountAmount = maxDiscountAmount,
                startAt = startAt,
                endAt = endAt,
                status = status,
                description = description
            )
        }

        fun of(
            id: Long,
            promotionName: String,
            promotionType: PromotionTypeEnums,
            discountValue: Long,
            minOrderAmount: Long,
            maxDiscountAmount: Long?,
            startAt: LocalDateTime,
            endAt: LocalDateTime,
            status: PromotionStatusEnums,
            description: String?
        ): PromotionModel {
            return PromotionModel(
                id = id,
                promotionName = promotionName,
                promotionType = promotionType,
                discountValue = discountValue,
                minOrderAmount = minOrderAmount,
                maxDiscountAmount = maxDiscountAmount,
                startAt = startAt,
                endAt = endAt,
                status = status,
                description = description
            )
        }
    }
}
