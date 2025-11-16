package com.gijun.kotlinecommerce.application.port.output.persistence.user

import com.gijun.kotlinecommerce.domain.common.PageRequest
import com.gijun.kotlinecommerce.domain.common.PageResponse
import com.gijun.kotlinecommerce.domain.user.model.UserModel

interface UserJpaPort {
    fun save(userModel: UserModel): UserModel
    fun findById(id: Long): UserModel?
    fun findByUserId(userId: String): UserModel?
    fun delete(userModel: UserModel): UserModel
    fun findAll(pageRequest: PageRequest): PageResponse<UserModel>
}