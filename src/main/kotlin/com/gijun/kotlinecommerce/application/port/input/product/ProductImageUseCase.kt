package com.gijun.kotlinecommerce.application.port.input.product

import com.gijun.kotlinecommerce.application.dto.command.product.productImage.UploadProductImageCommand
import com.gijun.kotlinecommerce.domain.product.model.ProductImageModel

interface ProductImageUseCase {
    fun uploadImages(command : List<UploadProductImageCommand>) : List<ProductImageModel>
    fun deleteImage(imageId: Long) : Boolean
    fun getProductImages(productId: Long) : List<ProductImageModel>
    fun getProductImageByIsMain(productId: Long) : ProductImageModel
}