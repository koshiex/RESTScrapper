package com.pricePilot.server.service

import com.pricePilot.server.model.Product
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.Cookie
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class ProductsScrapper : StoresScrapper {
    private lateinit var webDriver: WebDriver

    private val dnsScrapper by lazy { DNSScrapper(this) }

    override fun driverInit() {
        WebDriverManager.chromedriver().setup()

        val options = ChromeOptions()
//        options.addArguments("--headless")
        options.addArguments("--disable-gpu")
        options.addArguments("--no-sandbox")
        options.addArguments("--disable-dev-shm-usage")
        options.addArguments("--enable-javascript")
        options.addArguments("--disable-blink-features=AutomationControlled")
        options.addArguments("--user-agent='Mozilla/5.0 (Macintosh; " +
                "Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko)" +
                " Chrome/124.0.0.0 Safari/537.36'")

        webDriver = ChromeDriver(options)
    }


    override fun getHtml(url: String) : String? {
        if (!::webDriver.isInitialized) {
            return null
        }

        return try {
            webDriver.get(url)
            val allCookies: Set<Cookie> = webDriver.manage().cookies
            WebDriverWait(webDriver, Duration.ofSeconds(10))
            allCookies.forEach { cookie ->
                webDriver.manage().addCookie(cookie) }
            webDriver.navigate().refresh();
            webDriver.pageSource
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