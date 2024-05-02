package com.pricePilot.server.controller

import com.pricePilot.server.model.Product
import com.pricePilot.server.service.StoresScrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController() {
    private final lateinit var productsScrapper: StoresScrapper

    @Autowired
    public fun scrapController(scrapper: StoresScrapper) {
        productsScrapper = scrapper
    }

    @GetMapping( "/getHtml")
    public fun getHtml(@RequestParam url: String): ResponseEntity<String> {
        productsScrapper.driverInit()

        return try {
            val htmlStr = productsScrapper.getHtml(url)
            if (htmlStr != null) ResponseEntity.ok(htmlStr)
            else ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        } finally {
            productsScrapper.closeDriver()
        }
    }

    @GetMapping("/parseProducts")
    public fun parseProducts(@RequestParam productsName: String): ResponseEntity<List<Product?>> {
        return try {
            val products = productsScrapper.parseShops(productsName)
            ResponseEntity.ok(products)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}