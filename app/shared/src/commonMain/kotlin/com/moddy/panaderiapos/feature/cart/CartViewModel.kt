package com.moddy.panaderiapos.feature.cart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun addItem(item: CartItem) {
        _uiState.update { currentState ->
            currentState.copy(items = currentState.items + item)
        }
    }

    fun onEvent(
        event: CartEvent,
        onNavigateToCatalog: () -> Unit = {},
        onNavigateToHistory: () -> Unit = {}
    ) {
        when (event) {
            is CartEvent.OnRemoveItemClicked -> {
                _uiState.update { currentState ->
                    currentState.copy(items = currentState.items.filterNot { it.id == event.itemId })
                }
            }
            CartEvent.OnAddAnotherProductClicked -> {
                onNavigateToCatalog()
            }
            CartEvent.OnGenerateSaleClicked -> {
                // Limpiar carrito y navegar a registro de ventas
                _uiState.update { it.copy(items = emptyList()) }
                onNavigateToHistory()
            }
        }
    }
}