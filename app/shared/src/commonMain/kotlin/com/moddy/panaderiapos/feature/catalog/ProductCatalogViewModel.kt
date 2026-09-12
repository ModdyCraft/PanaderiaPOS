package com.moddy.panaderiapos.feature.catalog

import androidx.lifecycle.ViewModel
import com.moddy.panaderiapos.models.Product
import com.moddy.panaderiapos.models.SaleType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductCatalogViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    fun setProducts(products: List<Product>) {
        _uiState.update { it.copy(products = products) }
    }

    fun onEvent(
        event: CatalogEvent,
        onNavigateToPricing: (Product, SaleType) -> Unit = { _, _ -> }
    ) {
        when (event) {
            is CatalogEvent.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
            }
            is CatalogEvent.OnProductClicked -> {
                val hasWholesale = event.product.wholesalePricing != null
                val hasRetail = event.product.retailPricing != null

                if (hasWholesale && hasRetail) {
                    // Ambos precios disponibles: abrir diálogo local
                    _uiState.update { it.copy(selectedProductForSaleType = event.product) }
                } else {
                    // Solo uno disponible: saltar directo al calculador
                    val targetType = if (hasWholesale) SaleType.WHOLESALE else SaleType.RETAIL
                    onNavigateToPricing(event.product, targetType)
                }
            }
            CatalogEvent.OnDismissSaleTypeDialog -> {
                _uiState.update { it.copy(selectedProductForSaleType = null) }
            }
            is CatalogEvent.OnSaleTypeSelected -> {
                _uiState.update { it.copy(selectedProductForSaleType = null) }
                onNavigateToPricing(event.product, event.saleType)
            }
        }
    }
}