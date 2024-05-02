package com.pricePilot.server.service

import com.pricePilot.server.model.Product
import org.openqa.selenium.WebDriver

interface StoresScrapper {
    fun driverInit()
    fun getHtml(url: String) : String?
    fun parseShops(request: String) : List<Product?>
    fun closeDriver()
}