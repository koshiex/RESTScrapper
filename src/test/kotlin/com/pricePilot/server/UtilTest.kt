package com.pricePilot.server

import com.pricePilot.server.service.Util
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class UtilTest {

    @Test
    fun testPriceProcessor() {
        val price = "91 799 ₽101 999"
        val result = "91 799₽"
        assertEquals(result, Util.processPrice(price))
    }
}