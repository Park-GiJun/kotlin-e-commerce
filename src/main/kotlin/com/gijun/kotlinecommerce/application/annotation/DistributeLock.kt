package com.gijun.kotlinecommerce.application.annotation

import com.gijun.kotlinecommerce.domain.lock.model.DistributedLockType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributeLock(
    val type: DistributedLockType,
    val key: String,
    val waitTime: Long = -1L,
    val leaseTime: Long = -1L
    ) {
}