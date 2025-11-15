package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.user

import com.gijun.kotlinecommerce.domain.user.model.UserModel
import com.gijun.kotlinecommerce.domain.user.model.UserRole
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user")
class UserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var email: String = "",

    var name: String = "",

    var password: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: UserRole = UserRole.USER
) : BaseEntity() {

    fun toDomainModel(): UserModel {
        return UserModel(
            id = this.id ?: 0L,
            email = this.email,
            name = this.name,
            password = this.password,
            role = this.role
        )
    }

    companion object {
        fun fromDomain(model: UserModel): UserJpaEntity {
            return UserJpaEntity(
                id = model.id,
                email = model.email,
                name = model.name,
                password = model.password,
                role = model.role
            )
        }
    }
}