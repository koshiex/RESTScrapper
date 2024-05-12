package com.pricePilot.server.service

import com.pricePilot.server.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import org.openqa.selenium.WebDriver

interface StoresScrapper {
    fun driverInit(): WebDriver
    fun getHtml(url: String) : String?
//    fun parseShops(request: String) : List<Product?>
    fun parseShops(request: String): Deferred<List<Product?>>
}