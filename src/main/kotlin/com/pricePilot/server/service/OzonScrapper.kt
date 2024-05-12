package com.pricePilot.server.service

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.DomText
import com.gargoylesoftware.htmlunit.html.HtmlElement
import com.gargoylesoftware.htmlunit.html.HtmlImage
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.pricePilot.server.model.Product

class OzonScrapper(private val mainScrapper: StoresScrapper) : PageScrapper {
    private final val STORE_NAME = "Ozon"
    private final val OZON_SEARCH_URL = "https://www.ozon.ru/search/?from_global=true&sorting=price&text=%s"
    private final val OZON_URL = "https://www.ozon.ru/"

    private val webClient = WebClient().apply {
        options.isJavaScriptEnabled = false
        options.isCssEnabled = false
    }

    override fun scrapProduct(request: String): Product? {
        val url = OZON_SEARCH_URL.format(request)

        val htmlStr: String? = mainScrapper.getHtml(url)
        val page : HtmlPage? = webClient.loadHtmlCodeIntoCurrentWindow(htmlStr)

        return if (page != null) {
            Product(
                getName(page),
                STORE_NAME,
                getPrice(page),
                getPictureUrl(page),
                getCheaperProductUrl(page),
                url,
                OZON_URL
            )
        } else null
    }

    override fun getPictureUrl(page: HtmlPage): String? {
        val img = page.getFirstByXPath(
            "//*[@id=\"paginatorContent\"]/div[1]/div/div[1]/div/a/div/div[1]/img")
                as HtmlImage?
        return img?.srcAttribute
    }

    override fun getPrice(page: HtmlPage): String? {
        val xpath = "//span[contains(@class, 'tsHeadline500Medium')]"
        val element = page.getFirstByXPath(
            "/html/body/div[1]/div/div[1]/div[2]/div[2]/div[2]/div[4]/div/div[1]/div/div[1]/div/div[1]/div[1]/div/span[1]")
                as HtmlElement?

        return element?.textContent
    }

    override fun getName(page: HtmlPage): String? {
        val element = page.getFirstByXPath(
            "//*[@id=\"paginatorContent\"]/div[1]/div/div[1]/div/div[1]/a/div/span")
                as HtmlElement?
        return element?.textContent
    }

    override fun getCheaperProductUrl(page: HtmlPage): String? {
        val element = page.getFirstByXPath(
            "//*[@id=\"paginatorContent\"]/div[1]/div/div[1]/div/div[1]/a")
                as HtmlElement?
        val url = element?.getAttribute("href") ?: ""
        return OZON_URL + url
    }
}