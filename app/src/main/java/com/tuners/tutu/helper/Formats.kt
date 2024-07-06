package com.tuners.tutu.helper

import java.text.NumberFormat
import java.util.Locale

object Formats {
    fun String.withCurrencyFormat(): String {
        val rupiahExchangeRate = 16295.00

        // Convert the input string to a double value
        val priceOnDollar = this.toDouble()

        // Convert the price from dollars to rupiah
        val priceInRupiah = priceOnDollar * rupiahExchangeRate

        // Create a currency format instance for Indonesian Rupiah
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

        // Format the price in rupiah and return the string
        return currencyFormat.format(priceInRupiah)
    }
}