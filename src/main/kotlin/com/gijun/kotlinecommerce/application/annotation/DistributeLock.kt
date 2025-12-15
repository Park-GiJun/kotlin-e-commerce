package com.gijun.kotlinecommerce.application.annotation

import com.gijun.kotlinecommerce.domain.lock.model.DistributedLockTypeEnums

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributeLock(
    val type: DistributedLockTypeEnums,
    val key: String,
    val waitTime: Long = -1L,
    val leaseTime: Long = -1L
    ) {
}