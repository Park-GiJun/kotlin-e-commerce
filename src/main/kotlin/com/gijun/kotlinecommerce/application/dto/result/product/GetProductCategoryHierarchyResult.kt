package com.gijun.kotlinecommerce.application.dto.result.product

class GetProductCategoryHierarchyResult {
    var id: Long = 0
    var name: String = ""
    var depth: Int = 0
    var parentId: Long? = null
    var children: List<GetProductCategoryHierarchyResult> = listOf()
}