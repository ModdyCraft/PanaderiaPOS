package com.moddy.panaderiapos.feature.pricing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.moddy.panaderiapos.feature.cart.CartItem
import com.moddy.panaderiapos.formatDecimals
import com.moddy.panaderiapos.models.SaleType

@Composable
fun PricingCalculatorScreen(
    viewModel: PricingCalculatorViewModel,
    onItemAdded: (CartItem) -> Unit,
    onCancel: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val product = uiState.product

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .widthIn(max = 460.dp)
                .fillMaxWidth()
                .shadow(12.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Header: Nombre del Producto y Tipo de Venta
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = product?.name ?: "Producto",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    val typeText = if (uiState.saleType == SaleType.WHOLESALE) "Por Mayor" else "Por Menor"
                    val tierInfo = uiState.currentTier?.let { "${it.unitQuantity}x S/ ${it.price.formatDecimals()}" } ?: ""

                    Text(
                        text = "Venta $typeText ($tierInfo)",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Input Cantidad
                OutlinedTextField(
                    value = uiState.quantityInput,
                    onValueChange = { viewModel.onEvent(PricingCalculatorEvent.OnQuantityChanged(it), onItemAdded, onCancel) },
                    label = { Text("Cantidad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Input Precio Total
                OutlinedTextField(
                    value = uiState.priceInput,
                    onValueChange = { viewModel.onEvent(PricingCalculatorEvent.OnPriceChanged(it), onItemAdded, onCancel) },
                    label = { Text("Precio Total (S/)") },
                    singleLine = true,
                    readOnly = uiState.isPriceReadOnly,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    supportingText = if (uiState.isPriceReadOnly) {
                        { Text("Precio calculado por paquete en oferta.") }
                    } else null
                )

                // Botones de Acción (Cancelar / Añadir)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.onEvent(PricingCalculatorEvent.OnCancelClicked, onItemAdded, onCancel) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = { viewModel.onEvent(PricingCalculatorEvent.OnAddClicked, onItemAdded, onCancel) },
                        enabled = uiState.calculatedSubtotal > 0,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Añadir", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}