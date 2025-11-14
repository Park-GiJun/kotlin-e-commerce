package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    lateinit var createdAt: LocalDateTime
        private set

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    var createdBy: String? = null

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    lateinit var updatedAt: LocalDateTime
        private set

    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    var updatedBy: String? = null

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
        private set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        private set

    @Column(name = "deleted_by")
    var deletedBy: String? = null
}