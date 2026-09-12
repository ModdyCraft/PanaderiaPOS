package com.moddy.panaderiapos.feature.history

import com.moddy.panaderiapos.feature.cart.CartItem

data class SaleRecord(
    val id: String,
    val sellerId: String,
    val timestampFormatted: String, // Ej: "12/09/2026 13:45"
    val items: List<CartItem>,
    val total: Double
)

data class SalesHistoryUiState(
    val records: List<SaleRecord> = emptyList(),
    val selectedRecordForDetail: SaleRecord? = null,
    val isLoading: Boolean = false
)

sealed interface SalesHistoryEvent {
    data class OnRecordClicked(val record: SaleRecord) : SalesHistoryEvent
    data object OnDismissDetailDialog : SalesHistoryEvent
    data object OnBackClicked : SalesHistoryEvent
}