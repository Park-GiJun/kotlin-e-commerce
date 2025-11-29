package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.adapter

import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductJpaPort
import com.gijun.kotlinecommerce.domain.common.PageRequest
import com.gijun.kotlinecommerce.domain.product.model.ProductModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductJpaEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.ProductJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProductJpaPortAdapter(
    private val productJpaRepository: ProductJpaRepository
) : ProductJpaPort {

    override fun save(productModel: ProductModel): ProductModel {
        val entity = ProductJpaEntity.fromDomain(productModel)
        return productJpaRepository.save(entity).toDomainModel()
    }

    override fun update(productModel: ProductModel): ProductModel {
        val entity = ProductJpaEntity.fromDomain(productModel)
        return productJpaRepository.save(entity).toDomainModel()
    }

    override fun findAll(): List<ProductModel> {
        return productJpaRepository.findAll().map { it.toDomainModel() }
    }

    override fun findAllWithPaging(pageRequest: PageRequest): Pair<List<ProductModel>, Long> {
        val products = productJpaRepository.findAll()
            .drop(pageRequest.getOffset().toInt())
            .take(pageRequest.size)
            .map { it.toDomainModel() }

        val totalElements = productJpaRepository.count()

        return Pair(products, totalElements)
    }

    override fun findByCategory(categoryId: Long): List<ProductModel> {
        return productJpaRepository.findAll()
            .filter { it.productCategoryId == categoryId }
            .map { it.toDomainModel() }
    }

    override fun findByCategoryWithPaging(categoryId: Long, pageRequest: PageRequest): Pair<List<ProductModel>, Long> {
        val allProducts = productJpaRepository.findAll()
            .filter { it.productCategoryId == categoryId }

        val products = allProducts
            .drop(pageRequest.getOffset().toInt())
            .take(pageRequest.size)
            .map { it.toDomainModel() }

        val totalElements = allProducts.size.toLong()

        return Pair(products, totalElements)
    }

    override fun findById(id: Long): ProductModel? {
        return productJpaRepository.findByIdOrNull(id)?.toDomainModel()
    }

    override fun findByProductName(name: String): ProductModel? {
        return productJpaRepository.findAll()
            .firstOrNull { it.productName == name }
            ?.toDomainModel()
    }

    override fun delete(productModel: ProductModel): ProductModel {
        productModel.id?.let { productJpaRepository.deleteById(it) }
        return productModel
    }
}