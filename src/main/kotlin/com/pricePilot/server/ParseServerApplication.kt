package com.pricePilot.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParseServerApplication

fun main(args: Array<String>) {
    runApplication<ParseServerApplication>(*args)
}
