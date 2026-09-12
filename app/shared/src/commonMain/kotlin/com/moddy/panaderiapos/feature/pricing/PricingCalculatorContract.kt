package com.moddy.panaderiapos.feature.pricing

import com.moddy.panaderiapos.models.PricingTier
import com.moddy.panaderiapos.models.Product
import com.moddy.panaderiapos.models.SaleType

data class PricingCalculatorUiState(
    val product: Product? = null,
    val saleType: SaleType = SaleType.RETAIL,
    val currentTier: PricingTier? = null,
    val quantityInput: String = "1",
    val priceInput: String = "0.00",
    val calculatedQuantity: Double = 1.0,
    val calculatedSubtotal: Double = 0.0,
    val isPriceReadOnly: Boolean = false // Si es una oferta en paquete fijo (ej. 3x1 Sol), el precio suele ser de solo lectura
)

sealed interface PricingCalculatorEvent {
    data class OnQuantityChanged(val quantity: String) : PricingCalculatorEvent
    data class OnPriceChanged(val price: String) : PricingCalculatorEvent
    data object OnAddClicked : PricingCalculatorEvent
    data object OnCancelClicked : PricingCalculatorEvent
}