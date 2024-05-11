package com.pricePilot.server.service

import com.frogking.chromedriver.*
import com.pricePilot.server.model.Product
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Service
import java.time.Duration


@Service
class ProductsScrapper : StoresScrapper {
    private val dnsScrapper by lazy { DNSScrapper(this) }

    override fun driverInit(): WebDriver {
        val driverExecutablePath = System.getProperty("webdriver.chrome.driver")

        val options = ChromeOptions()
        options.addArguments("--disable-gpu")
        options.addArguments("--no-sandbox")
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-dev-shm-usage")
        options.addArguments("--enable-javascript")
        options.addArguments("--disable-blink-features=AutomationControlled")
        options.addArguments("--user-agent='Mozilla/5.0 (Macintosh; " +
                "Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko)" +
                " Chrome/124.0.0.0 Safari/537.36'")

        return ChromeDriverBuilder().build(options, driverExecutablePath)
    }


    override fun getHtml(url: String) : String? {
        val webDriver = driverInit()

        return try {
            webDriver.get(url)
            val allCookies: Set<Cookie> = webDriver.manage().cookies
            WebDriverWait(webDriver, Duration.ofSeconds(10))
            allCookies.forEach { cookie ->
                webDriver.manage().addCookie(cookie) }
            webDriver.navigate().refresh();

            webDriver.pageSource
        } catch (e: WebDriverException) {
            println(e)
            null
        } finally {
            webDriver.close()
        }
    }


    override fun parseShops(request: String): List<Product?> =
        listOf(
            dnsScrapper.scrapProduct(request)
        )

}