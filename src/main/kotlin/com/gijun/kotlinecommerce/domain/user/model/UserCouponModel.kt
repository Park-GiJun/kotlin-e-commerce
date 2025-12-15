package com.gijun.kotlinecommerce.domain.user.model

import java.time.LocalDateTime

data class UserCouponModel(
    val id: Long?,
    val userId: Long,
    val couponId: Long,
    val status: UserCouponStatusEnums,
    val issuedAt: LocalDateTime,
    val usedAt: LocalDateTime?,
    val expiredAt: LocalDateTime
) {
    companion object {
        fun create(
            userId: Long,
            couponId: Long,
            expiredAt: LocalDateTime,
            status: UserCouponStatusEnums = UserCouponStatusEnums.AVAILABLE,
            issuedAt: LocalDateTime = LocalDateTime.now()
        ): UserCouponModel {
            return UserCouponModel(
                id = null,
                userId = userId,
                couponId = couponId,
                status = status,
                issuedAt = issuedAt,
                usedAt = null,
                expiredAt = expiredAt
            )
        }

        fun of(
            id: Long,
            userId: Long,
            couponId: Long,
            status: UserCouponStatusEnums,
            issuedAt: LocalDateTime,
            usedAt: LocalDateTime?,
            expiredAt: LocalDateTime
        ): UserCouponModel {
            return UserCouponModel(
                id = id,
                userId = userId,
                couponId = couponId,
                status = status,
                issuedAt = issuedAt,
                usedAt = usedAt,
                expiredAt = expiredAt
            )
        }
    }
}
