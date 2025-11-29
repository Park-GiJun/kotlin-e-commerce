package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.adapter

import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductCategoryJpaPort
import com.gijun.kotlinecommerce.domain.common.PageRequest
import com.gijun.kotlinecommerce.domain.product.model.ProductCategoryModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductCategoryJpaEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.ProductCategoryJpaRepository
import org.springframework.data.domain.PageRequest as SpringPageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProductCategoryPortAdapter(
    private val productCategoryJpaRepository: ProductCategoryJpaRepository
) : ProductCategoryJpaPort {

    override fun save(productCategory: ProductCategoryModel): ProductCategoryModel {
        val entity = ProductCategoryJpaEntity.fromDomain(productCategory)
        return productCategoryJpaRepository.save(entity).toDomainModel()
    }

    override fun findAll(): List<ProductCategoryModel> {
        return productCategoryJpaRepository.findAll().map { it.toDomainModel() }
    }

    override fun findAllWithPaging(pageRequest: PageRequest): Pair<List<ProductCategoryModel>, Long> {
        val springPageRequest = SpringPageRequest.of(pageRequest.page, pageRequest.size)
        val page = productCategoryJpaRepository.findAll(springPageRequest)

        return Pair(
            page.content.map { it.toDomainModel() },
            page.totalElements
        )
    }

    override fun findById(id: Long): ProductCategoryModel? {
        return productCategoryJpaRepository.findByIdOrNull(id)?.toDomainModel()
    }

    override fun findByCategoryName(name: String): ProductCategoryModel? {
        return productCategoryJpaRepository.findAll()
            .firstOrNull { it.categoryName == name }
            ?.toDomainModel()
    }

    override fun update(productCategory: ProductCategoryModel): ProductCategoryModel {
        val entity = ProductCategoryJpaEntity.fromDomain(productCategory)
        return productCategoryJpaRepository.save(entity).toDomainModel()
    }

    override fun delete(id: Long): ProductCategoryModel {
        val entity = productCategoryJpaRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Category not found")
        productCategoryJpaRepository.deleteById(id)
        return entity.toDomainModel()
    }

    override fun findProductHierarchies(): List<ProductCategoryModel> {
        return productCategoryJpaRepository.findAll().map { it.toDomainModel() }
    }
}