package com.gijun.kotlinecommerce.domain.user.model

data class UserPointModel(
    val id: Long?,
    val userId: Long,
    val totalPoint: Int,
    val availablePoint: Int,
    val usedPoint: Int
) {
    companion object {
        fun create(
            userId: Long,
            totalPoint: Int = 0,
            availablePoint: Int = 0,
            usedPoint: Int = 0
        ): UserPointModel {
            return UserPointModel(
                id = null,
                userId = userId,
                totalPoint = totalPoint,
                availablePoint = availablePoint,
                usedPoint = usedPoint
            )
        }

        fun of(
            id: Long,
            userId: Long,
            totalPoint: Int,
            availablePoint: Int,
            usedPoint: Int
        ): UserPointModel {
            return UserPointModel(
                id = id,
                userId = userId,
                totalPoint = totalPoint,
                availablePoint = availablePoint,
                usedPoint = usedPoint
            )
        }
    }
}
