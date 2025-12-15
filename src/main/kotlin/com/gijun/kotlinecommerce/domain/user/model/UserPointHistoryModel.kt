package com.gijun.kotlinecommerce.domain.user.model

import java.time.LocalDateTime

data class UserPointHistoryModel(
    val id: Long?,
    val userId: Long,
    val pointType: PointTypeEnums,
    val point: Int,
    val balanceAfter: Int,
    val description: String?,
    val orderId: Long?,
    val createdAt: LocalDateTime
) {
    companion object {
        fun create(
            userId: Long,
            pointType: PointTypeEnums,
            point: Int,
            balanceAfter: Int,
            description: String? = null,
            orderId: Long? = null,
            createdAt: LocalDateTime = LocalDateTime.now()
        ): UserPointHistoryModel {
            return UserPointHistoryModel(
                id = null,
                userId = userId,
                pointType = pointType,
                point = point,
                balanceAfter = balanceAfter,
                description = description,
                orderId = orderId,
                createdAt = createdAt
            )
        }

        fun of(
            id: Long,
            userId: Long,
            pointType: PointTypeEnums,
            point: Int,
            balanceAfter: Int,
            description: String?,
            orderId: Long?,
            createdAt: LocalDateTime
        ): UserPointHistoryModel {
            return UserPointHistoryModel(
                id = id,
                userId = userId,
                pointType = pointType,
                point = point,
                balanceAfter = balanceAfter,
                description = description,
                orderId = orderId,
                createdAt = createdAt
            )
        }
    }
}
