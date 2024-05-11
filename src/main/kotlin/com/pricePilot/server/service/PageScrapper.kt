package com.pricePilot.server.service

import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.pricePilot.server.model.Product

interface PageScrapper {
    fun scrapProduct(request: String) : Product?
    fun getPictureUrl(page: HtmlPage) : String?
    fun getPrice(page: HtmlPage) : String?
    fun getName(page: HtmlPage) : String?
    fun getCheaperProductUrl(page: HtmlPage) : String?
}