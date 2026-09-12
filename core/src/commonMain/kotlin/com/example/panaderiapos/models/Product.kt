package com.example.panaderiapos.models

import kotlinx.serialization.Serializable

@Serializable
enum class SaleType { WHOLESALE, RETAIL }

@Serializable
data class PricingTier(
    val unitQuantity: Int, // Cantidad de unidades (ej. 5 o 1)
    val price: Double      // Precio del paquete o unidad en Soles
)

@Serializable
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val wholesalePricing: PricingTier?,
    val retailPricing: PricingTier?
)