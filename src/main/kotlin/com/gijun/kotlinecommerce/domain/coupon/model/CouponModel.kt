package com.gijun.kotlinecommerce.domain.coupon.model

import java.time.LocalDateTime

data class CouponModel(
    val id: Long?,
    val couponCode: String,
    val couponName: String,
    val couponType: CouponTypeEnums,
    val discountValue: Long,
    val minOrderAmount: Long,
    val maxDiscountAmount: Long?,
    val totalQuantity: Int,
    val issuedQuantity: Int,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val status: CouponStatusEnums
) {
    companion object {
        fun create(
            couponCode: String,
            couponName: String,
            couponType: CouponTypeEnums,
            discountValue: Long,
            minOrderAmount: Long = 0,
            maxDiscountAmount: Long? = null,
            totalQuantity: Int,
            startAt: LocalDateTime,
            endAt: LocalDateTime,
            status: CouponStatusEnums = CouponStatusEnums.ACTIVE
        ): CouponModel {
            return CouponModel(
                id = null,
                couponCode = couponCode,
                couponName = couponName,
                couponType = couponType,
                discountValue = discountValue,
                minOrderAmount = minOrderAmount,
                maxDiscountAmount = maxDiscountAmount,
                totalQuantity = totalQuantity,
                issuedQuantity = 0,
                startAt = startAt,
                endAt = endAt,
                status = status
            )
        }

        fun of(
            id: Long,
            couponCode: String,
            couponName: String,
            couponType: CouponTypeEnums,
            discountValue: Long,
            minOrderAmount: Long,
            maxDiscountAmount: Long?,
            totalQuantity: Int,
            issuedQuantity: Int,
            startAt: LocalDateTime,
            endAt: LocalDateTime,
            status: CouponStatusEnums
        ): CouponModel {
            return CouponModel(
                id = id,
                couponCode = couponCode,
                couponName = couponName,
                couponType = couponType,
                discountValue = discountValue,
                minOrderAmount = minOrderAmount,
                maxDiscountAmount = maxDiscountAmount,
                totalQuantity = totalQuantity,
                issuedQuantity = issuedQuantity,
                startAt = startAt,
                endAt = endAt,
                status = status
            )
        }
    }
}
