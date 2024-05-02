package com.pricePilot.server.service

import com.pricePilot.server.model.Product
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.safari.SafariDriver
import org.openqa.selenium.safari.SafariOptions
import org.springframework.stereotype.Service

@Service
class ProductsScrapper : StoresScrapper {
    private lateinit var webDriver: WebDriver

    private val dnsScrapper by lazy { DNSScrapper(this) }

    override fun driverInit() {
        WebDriverManager.chromedriver().setup()

        val options = ChromeOptions()
        options.addArguments("--headless")
        options.addArguments("--disable-gpu")
        options.addArguments("--no-sandbox")
        options.addArguments("--disable-dev-shm-usage")

        webDriver = ChromeDriver(options)
    }


    override fun getHtml(url: String) : String? {
        if (!::webDriver.isInitialized) {
            return null
        }

        return try {
            webDriver.get(url);
            webDriver.pageSource;
        } catch (e: WebDriverException) {
            null
        }
    }


    override fun parseShops(request: String): List<Product?> {
        driverInit()

        val products = listOf(
            dnsScrapper.scrapProduct(request)
        )

        closeDriver()

        return products
    }

    override fun closeDriver() {
        webDriver.quit()
    }
}