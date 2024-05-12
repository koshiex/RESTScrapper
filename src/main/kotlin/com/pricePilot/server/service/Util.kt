package com.pricePilot.server.service

object Util{
    fun processPrice(input: String): String {
        if (input.indexOf('₽') == -1) return input
        val trimmed = input
            .substring(0, input.indexOf('₽') + 1)
            .replace(" ", "")

        val parts = trimmed.split(" ")
        return if (parts.size > 2) {
            parts.subList(0, 2).joinToString(" ") +
                    parts.subList(2, parts.size).joinToString("")
        } else {
            trimmed
        }
    }
}