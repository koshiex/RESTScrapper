package com.pricePilot.server.service

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlElement
import com.gargoylesoftware.htmlunit.html.HtmlImage
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.pricePilot.server.model.Product


class DNSScrapper(private val mainScrapper: StoresScrapper) : PageScrapper {
    final val STORE_NAME = "DNS"
    final val DNS_SEARCH_URL = "https://www.dns-shop.ru/search/?q=%s&order=price-asc"
    final val DNS_URL = "https://www.dns-shop.ru"

    private val webClient = WebClient().apply {
        options.isJavaScriptEnabled = false
        options.isCssEnabled = false
    }

    override fun scrapProduct(request: String): Product? {
        val url = DNS_SEARCH_URL.format(request)
        val page: HtmlPage
        val htmlStr: String?

        try {
            htmlStr = mainScrapper.getHtml(request)
            page = webClient.loadHtmlCodeIntoCurrentWindow(htmlStr)
        } catch (e: Exception) {
            return null
        }

        return Product(
            getName(page),
            STORE_NAME,
            getPrice(page),
            getPictureUrl(page),
            url,
            getCheaperProductUrl(page),
            DNS_URL
        )
    }

    override fun getPictureUrl(page: HtmlPage): String {
        val img = page.getFirstByXPath(
            "//*[@id='search-results']/div[2]/div/div[1]/div[1]/div[1]/a/picture/img")
                as HtmlImage
        return img.srcAttribute
    }

    override fun getPrice(page: HtmlPage): String {
        val element = page.getFirstByXPath(
            "//*[@id='search-results']/div[2]/div/div[1]/div[1]/div[4]/div/div[1]")
            as HtmlElement

        return element.textContent.replace("\u00a0", " ");
    }

    override fun getName(page: HtmlPage): String {
        val img = page.getFirstByXPath(
            "//*[@id='search-results']/div[2]/div/div[1]/div[1]/div[1]/a/picture/img")
                as HtmlElement
        return img.getAttribute("alt")
    }

    override fun getCheaperProductUrl(page: HtmlPage): String {
        val element = page.getFirstByXPath(
            "//*[@id=\"search-results\"]/div[2]/div/div[1]/div[1]/a")
                as HtmlElement
        val url = element.getAttribute("href")
        return DNS_URL + url
    }
}