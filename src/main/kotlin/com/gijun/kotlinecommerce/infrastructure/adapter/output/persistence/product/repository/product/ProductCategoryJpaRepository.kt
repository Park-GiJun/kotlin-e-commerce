package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository.product

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.product.ProductCategoryJpaEntity
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCategoryJpaRepository : JpaRepository<ProductCategoryJpaEntity, Long>, KotlinJdslJpqlExecutor {
}