package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.user

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

    var userId: String = "",

    var name: String = "",

    var password: String = ""
) : BaseEntity() {

}