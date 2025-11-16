package com.gijun.kotlinecommerce.domain.common

data class PageRequest(
    val page: Int = 0,
    val size: Int = 10
) {
    init {
        require(page >= 0) { "Page must be non-negative" }
        require(size > 0) { "Size must be positive" }
        require(size <= MAX_PAGE_SIZE) { "Size must not exceed $MAX_PAGE_SIZE" }
    }

    fun getOffset(): Long = (page * size).toLong()

    companion object {
        const val MAX_PAGE_SIZE = 100
        val DEFAULT = PageRequest()
    }
}