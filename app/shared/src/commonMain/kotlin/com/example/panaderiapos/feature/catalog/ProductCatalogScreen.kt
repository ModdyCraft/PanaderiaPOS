package com.example.panaderiapos.feature.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.panaderiapos.models.Product
import com.example.panaderiapos.models.SaleType

@Composable
fun ProductCatalogScreen(
    viewModel: ProductCatalogViewModel,
    onNavigateToPricing: (Product, SaleType) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedCategoryFilter by remember { mutableStateOf<String?>(null) }

    // Obtener todas las categorías disponibles para los chips
    val categories = remember(uiState.products) {
        uiState.products.map { it.category }.distinct()
    }

    // Filtrar por texto y por categoría seleccionada
    val filteredProducts = uiState.products.filter { product ->
        val matchesQuery = product.name.contains(uiState.searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategoryFilter == null || product.category == selectedCategoryFilter
        matchesQuery && matchesCategory
    }.groupBy { it.category }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp) // Separación entre Panel de Búsqueda y Catálogo
    ) {
        // --- PANEL 1: BÚSQUEDA Y CATEGORÍAS ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // OutlinedTextField de Búsqueda
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.onEvent(CatalogEvent.OnSearchQueryChanged(it)) },
                    label = { Text("Buscar producto...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Chips / Tooltips seleccionables de Categorías
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategoryFilter == null,
                            onClick = { selectedCategoryFilter = null },
                            label = { Text("Todas") }
                        )
                    }
                    items(categories) { category ->
                        FilterChip(
                            selected = selectedCategoryFilter == category,
                            onClick = {
                                selectedCategoryFilter = if (selectedCategoryFilter == category) null else category
                            },
                            label = { Text(category) }
                        )
                    }
                }
            }
        }

        // --- PANEL 2: CATÁLOGO DE PRODUCTOS (GRID VERTICAL) ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .shadow(8.dp, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 220.dp),
                contentPadding = PaddingValues(20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                filteredProducts.forEach { (category, products) ->
                    // Header de Categoría que ocupa todo el ancho de la Grid
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // Product Cards
                    items(products) { product ->
                        ProductGridItemCard(
                            product = product,
                            onClick = {
                                viewModel.onEvent(CatalogEvent.OnProductClicked(product), onNavigateToPricing)
                            }
                        )
                    }
                }
            }
        }
    }

    // Diálogo "Vender al"
    uiState.selectedProductForSaleType?.let { product ->
        SaleTypeDialog(
            product = product,
            onDismiss = { viewModel.onEvent(CatalogEvent.OnDismissSaleTypeDialog) },
            onSelectSaleType = { saleType ->
                viewModel.onEvent(CatalogEvent.OnSaleTypeSelected(product, saleType), onNavigateToPricing)
            }
        )
    }
}

// --- PRODUCT CARD ITEM ---

@Composable
private fun ProductGridItemCard(
    product: Product,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder().copy(width = 1.5.dp) // Contorno definido para resaltar
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Placeholder de imagen con fondo gris
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sin Imagen",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Información del Producto
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            val retailText = product.retailPricing?.let { "${it.unitQuantity}x S/${it.price}" } ?: "-"
            val wholesaleText = product.wholesalePricing?.let { "${it.unitQuantity}x S/${it.price}" } ?: "-"

            Text(
                text = "Menor: $retailText",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = "Mayor: $wholesaleText",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}