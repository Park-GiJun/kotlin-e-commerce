package com.gijun.kotlinecommerce.infrastructure.aop

class DistributedLockException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)