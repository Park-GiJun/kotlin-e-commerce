package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.adapter.product

import com.gijun.kotlinecommerce.application.port.output.persistence.product.ProductJpaPort
import com.gijun.kotlinecommerce.domain.product.model.ProductModel
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.product.ProductJpaEntity
import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.product.ProductJpaRepository
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

    override fun findByCategory(categoryId: Long): List<ProductModel> {
        return productJpaRepository.findAll()
            .filter { it.productCategoryId == categoryId }
            .map { it.toDomainModel() }
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