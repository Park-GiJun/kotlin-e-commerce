package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.adapter.user

import com.gijun.kotlinecommerce.application.port.output.persistence.user.UserJpaPort
import com.gijun.kotlinecommerce.domain.user.model.UserModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.user.UserJpaEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.user.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserJpaPortAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserJpaPort {

    override fun save(userModel: UserModel): UserModel {
        val entity = UserJpaEntity.fromDomain(userModel)
        return userJpaRepository.save(entity).toDomainModel()
    }

    override fun findById(id: Long): UserModel? {
        return userJpaRepository.findByIdOrNull(id)?.toDomainModel()
    }

    override fun findByUserId(userId: String): UserModel? {
        return userJpaRepository.findByEmail(userId)?.toDomainModel()
    }

    override fun delete(userModel: UserModel): UserModel {
        userModel.id?.let { userJpaRepository.deleteById(it) }
        return userModel
    }
}
