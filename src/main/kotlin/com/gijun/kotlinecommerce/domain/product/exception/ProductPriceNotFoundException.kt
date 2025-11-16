package com.gijun.kotlinecommerce.domain.product.exception

class ProductPriceNotFoundException(
    productPriceId: Long
) : RuntimeException("ProductPrice with ID $productPriceId not found")
