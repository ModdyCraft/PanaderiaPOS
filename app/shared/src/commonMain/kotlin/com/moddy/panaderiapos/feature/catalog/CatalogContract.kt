package com.moddy.panaderiapos.feature.catalog

import com.moddy.panaderiapos.models.Product
import com.moddy.panaderiapos.models.SaleType

data class CatalogUiState(
    val products: List<Product> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val selectedProductForSaleType: Product? = null,
    val errorMessage: String? = null
) {
    // Agrupa y filtra los productos automáticamente por categoría
    val filteredCategories: Map<String, List<Product>>
        get() = products
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .groupBy { it.category }
}

sealed interface CatalogEvent {
    data class OnSearchQueryChanged(val query: String) : CatalogEvent
    data class OnProductClicked(val product: Product) : CatalogEvent
    data object OnDismissSaleTypeDialog : CatalogEvent
    data class OnSaleTypeSelected(val product: Product, val saleType: SaleType) : CatalogEvent
}