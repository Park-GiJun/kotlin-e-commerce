package com.gijun.kotlinecommerce.infrastructure.adapter.input.scheduler.product

import com.gijun.kotlinecommerce.application.port.input.product.ProductPriceUseCase
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDate

class ProductPriceScheduler(
    private val productPricePriceUseCase: ProductPriceUseCase
) {

    @Scheduled(cron = "0 0 00 * * *")
    fun updateAllProductPrice() {
        productPricePriceUseCase.updateAllProductPriceByToDay(LocalDate.now())
    }
}