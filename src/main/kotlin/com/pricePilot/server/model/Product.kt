package com.pricePilot.server.model

data class Product(
    val productName: String?, val marketName: String?,
    val productPrice: String?, val picUrl: String?,
    val productUrl: String? ,val sameProductsUrl: String?,
    val storeURL: String?) {
}