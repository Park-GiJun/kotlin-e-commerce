package com.gijun.kotlinecommerce.domain.lock.model

enum class DistributedLockType(
    val waitTime: Long = 0L,
    val leaseTime: Long = 30L,
    val retryTimes: Int = 3
) {
    
}