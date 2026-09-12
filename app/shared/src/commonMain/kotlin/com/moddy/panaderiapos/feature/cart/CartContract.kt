package com.moddy.panaderiapos.feature.cart

import com.moddy.panaderiapos.models.Product
import com.moddy.panaderiapos.models.SaleType

data class CartItem(
    val id: String,
    val product: Product,
    val saleType: SaleType,
    val quantity: Double,
    val unitOrTierDescription: String, // Ej: "5x S/ 1.00" o "S/ 5.80 u."
    val subtotal: Double
)

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val isLoading: Boolean = false
) {
    val grandTotal: Double
        get() = items.sumOf { it.subtotal }
}

sealed interface CartEvent {
    data class OnRemoveItemClicked(val itemId: String) : CartEvent
    data object OnAddAnotherProductClicked : CartEvent
    data object OnGenerateSaleClicked : CartEvent
}