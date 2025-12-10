package com.gijun.kotlinecommerce.application.handler.product

import com.gijun.kotlinecommerce.application.dto.command.product.productImage.UploadProductImageCommand
import com.gijun.kotlinecommerce.application.port.input.product.ProductImageUseCase
import com.gijun.kotlinecommerce.domain.product.model.ProductImageModel

class ProductImageHandler(

) : ProductImageUseCase {
    override fun uploadImages(command: List<UploadProductImageCommand>): List<ProductImageModel> {
        TODO("Not yet implemented")
    }

    override fun deleteImage(imageId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun getProductImages(productId: Long): List<ProductImageModel> {
        TODO("Not yet implemented")
    }

    override fun getProductImageByIsMain(productId: Long): ProductImageModel {
        TODO("Not yet implemented")
    }
}