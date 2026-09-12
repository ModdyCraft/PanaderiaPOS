package com.moddy.panaderiapos.feature.pricing

import androidx.lifecycle.ViewModel
import com.moddy.panaderiapos.feature.cart.CartItem
import com.moddy.panaderiapos.formatDecimals
import com.moddy.panaderiapos.models.Product
import com.moddy.panaderiapos.models.SaleType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Clock
import kotlin.time.TimeMark
import kotlin.time.TimeSource

class PricingCalculatorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PricingCalculatorUiState())
    val uiState: StateFlow<PricingCalculatorUiState> = _uiState.asStateFlow()

    fun setupProduct(product: Product, saleType: SaleType) {
        val tier = if (saleType == SaleType.WHOLESALE) product.wholesalePricing else product.retailPricing

        // Si el tier especifica más de 1 unidad (ej. 5 por 1 sol), bloqueamos la modificación directa del precio para evitar descuadres
        val isReadOnly = (tier?.unitQuantity ?: 1) > 1

        _uiState.update {
            it.copy(
                product = product,
                saleType = saleType,
                currentTier = tier,
                quantityInput = "1",
                isPriceReadOnly = isReadOnly
            )
        }
        recalculateFromQuantity("1")
    }

    fun onEvent(
        event: PricingCalculatorEvent,
        onItemAdded: (CartItem) -> Unit,
        onCancel: () -> Unit
    ) {
        when (event) {
            is PricingCalculatorEvent.OnQuantityChanged -> {
                // Solo permite caracteres numéricos
                val filtered = event.quantity.filter { it.isDigit() || it == '.' }
                _uiState.update { it.copy(quantityInput = filtered) }
                recalculateFromQuantity(filtered)
            }
            is PricingCalculatorEvent.OnPriceChanged -> {
                if (_uiState.value.isPriceReadOnly) return
                val filtered = event.price.filter { it.isDigit() || it == '.' }
                _uiState.update { it.copy(priceInput = filtered) }
                recalculateFromPrice(filtered)
            }
            PricingCalculatorEvent.OnAddClicked -> {
                val state = _uiState.value
                val product = state.product ?: return
                val tier = state.currentTier

                val description = if (tier != null) {
                    "${tier.unitQuantity}x S/ ${tier.price.formatDecimals()}"
                } else "S/ 0.00"

                val newItem = CartItem(
                    id = "${product.id}_${state.saleType.name}_${TimeSource.Monotonic.markNow()}",
                    product = product,
                    saleType = state.saleType,
                    quantity = state.calculatedQuantity,
                    unitOrTierDescription = description,
                    subtotal = state.calculatedSubtotal
                )

                onItemAdded(newItem)
            }
            PricingCalculatorEvent.OnCancelClicked -> {
                onCancel()
            }
        }
    }

    private fun recalculateFromQuantity(cantStr: String) {
        val cant = cantStr.toDoubleOrNull() ?: 0.0
        val tier = _uiState.value.currentTier ?: return

        val subtotal = if (tier.unitQuantity > 1) {
            // Regla por paquetes/oferta (ej: 5 panes por 1 sol)
            val paquetes = cant.toInt() / tier.unitQuantity
            val sobrantes = cant.toInt() % tier.unitQuantity
            val precioPorUnidadSuelta = tier.price / tier.unitQuantity

            (paquetes * tier.price) + (sobrantes * precioPorUnidadSuelta)
        } else {
            // Precio unitario directo
            cant * tier.price
        }

        _uiState.update {
            it.copy(
                calculatedQuantity = cant,
                calculatedSubtotal = subtotal,
                priceInput = subtotal.formatDecimals()
            )
        }
    }

    private fun recalculateFromPrice(priceStr: String) {
        val price = priceStr.toDoubleOrNull() ?: 0.0
        val tier = _uiState.value.currentTier ?: return

        if (tier.price > 0 && tier.unitQuantity == 1) {
            val cant = price / tier.price
            _uiState.update {
                it.copy(
                    calculatedQuantity = cant,
                    calculatedSubtotal = price,
                    quantityInput = if (cant % 1.0 == 0.0) cant.toInt().toString() else cant.formatDecimals(1)
                )
            }
        }
    }
}