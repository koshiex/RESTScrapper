package com.pricePilot.server

import io.github.bonigarcia.wdm.WebDriverManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParseServerApplication

fun main(args: Array<String>) {
    WebDriverManager.chromedriver().setup()
    runApplication<ParseServerApplication>(*args)
}
