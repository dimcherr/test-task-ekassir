package pics.phocus.testtaskekassir.internal

import java.util.*

object PriceFormatter {
    fun format(price: Int, currencyCode: String): String {
        val currency = Currency.getInstance(currencyCode)
        val priceString = price.toString()
        val midIndex = priceString.length - currency.defaultFractionDigits
        val integerPart = priceString.slice(0 until midIndex)
        val fractionPart = priceString.substring(midIndex)

        // fancy symbol for RUB
        val symbol = if (currencyCode == "RUB") 0x20BD.toChar().toString() else currency.symbol

        // omit fraction part if 00
        return if (fractionPart == "00") "$symbol $integerPart" else "$symbol $integerPart.$fractionPart"
    }
}