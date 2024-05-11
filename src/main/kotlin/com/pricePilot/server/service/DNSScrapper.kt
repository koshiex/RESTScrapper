package com.pricePilot.server.service

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlElement
import com.gargoylesoftware.htmlunit.html.HtmlImage
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.pricePilot.server.model.Product


class DNSScrapper(private val mainScrapper: StoresScrapper) : PageScrapper {
    private final val STORE_NAME = "DNS"
    private final val DNS_SEARCH_URL = "https://www.dns-shop.ru/search/?q=%s&order=price-asc"
    private final val DNS_URL = "https://www.dns-shop.ru"

    private val webClient = WebClient().apply {
        options.isJavaScriptEnabled = false
        options.isCssEnabled = false
    }

    override fun scrapProduct(request: String): Product? {
        val url = DNS_SEARCH_URL.format(request)

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
                DNS_URL
            )
        } else null
    }

    override fun getPictureUrl(page: HtmlPage): String? {
        val img = page.getFirstByXPath(
            "//*[@id='search-results']/div[2]/div/div[1]/div[1]/div[1]/a/picture/img")
                as HtmlImage?
        return img?.srcAttribute
    }

    override fun getPrice(page: HtmlPage): String? {
        val element = page.getFirstByXPath(
            "//*[@id=\"search-results\"]/div[2]/div/div[1]/div[1]/div[4]/div/div[1]/text()")
            as HtmlElement?

        return element?.textContent?.replace("\u00a0", " ");
    }

    override fun getName(page: HtmlPage): String? {
        val img = page.getFirstByXPath(
            "//*[@id='search-results']/div[2]/div/div[1]/div[1]/div[1]/a/picture/img")
                as HtmlElement?
        return img?.getAttribute("alt")
    }

    override fun getCheaperProductUrl(page: HtmlPage): String? {
        val element = page.getFirstByXPath(
            "//*[@id=\"search-results\"]/div[2]/div/div[1]/div[1]/a")
                as HtmlElement?
        val url = element?.getAttribute("href") ?: ""
        return DNS_URL + url
    }
}