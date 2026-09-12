package com.moddy.panaderiapos

// Agrega esta función utilitaria en core o commonMain
fun Double.formatDecimals(decimals: Int = 2): String {
    val factor = when (decimals) {
        1 -> 10.0
        2 -> 100.0
        3 -> 1000.0
        else -> 100.0
    }
    val rounded = kotlin.math.round(this * factor) / factor
    val parts = rounded.toString().split(".")
    val integerPart = parts[0]
    val decimalPart = parts.getOrNull(1)?.padEnd(decimals, '0')?.take(decimals) ?: "0".repeat(decimals)

    return "$integerPart.$decimalPart"
}