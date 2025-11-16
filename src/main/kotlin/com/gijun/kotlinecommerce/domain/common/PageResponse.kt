package com.gijun.kotlinecommerce.domain.common

data class PageResponse<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int
) {
    companion object {
        fun <T> of(content: List<T>, pageRequest: PageRequest, totalElements: Long): PageResponse<T> {
            val totalPages = if (pageRequest.size == 0) 1 else
                ((totalElements + pageRequest.size - 1) / pageRequest.size).toInt()

            return PageResponse(
                content = content,
                page = pageRequest.page,
                size = pageRequest.size,
                totalElements = totalElements,
                totalPages = totalPages
            )
        }

        fun <T> empty(): PageResponse<T> {
            return PageResponse(
                content = emptyList(),
                page = 0,
                size = 0,
                totalElements = 0,
                totalPages = 0
            )
        }
    }
}