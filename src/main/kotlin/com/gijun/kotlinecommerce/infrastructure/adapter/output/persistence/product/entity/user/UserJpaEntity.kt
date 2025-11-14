package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.user

import com.gijun.kotlinecommerce.domain.user.model.UserModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
class UserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var email: String = "",

    var name: String = "",

    var password: String = ""
) : BaseEntity() {

    fun toDomainModel(): UserModel {
        return UserModel(
            id = this.id ?: 0L,
            email = this.email,
            name = this.name,
            password = this.password
        )
    }

    companion object {
        fun fromDomain(model: UserModel): UserJpaEntity {
            return UserJpaEntity(
                id = model.id,
                email = model.email,
                name = model.name,
                password = model.password
            )
        }
    }
}