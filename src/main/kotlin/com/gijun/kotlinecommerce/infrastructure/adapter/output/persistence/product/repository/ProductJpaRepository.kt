package com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.repository

import com.gijun.kotlinecommerce.infrastructure.adapter.output.persistence.product.entity.ProductJpaEntity
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<ProductJpaEntity, Long>, KotlinJdslJpqlExecutor {
}