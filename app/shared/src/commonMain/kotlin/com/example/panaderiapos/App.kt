package com.example.panaderiapos

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.panaderiapos.core.theme.PanaderiaTheme
import com.example.panaderiapos.feature.auth.LoginScreen
import com.example.panaderiapos.feature.auth.LoginViewModel
import com.example.panaderiapos.feature.catalog.ProductCatalogScreen
import com.example.panaderiapos.feature.catalog.ProductCatalogViewModel
import com.example.panaderiapos.models.Product
import com.example.panaderiapos.models.SaleType

@Suppress("ViewModelConstructorInComposable")
@Composable
@Preview
fun App() {
    PanaderiaTheme {
        // LoginScreen(viewModel = LoginViewModel())
        /*
        ProductCatalogScreen(
            viewModel = ProductCatalogViewModel(),
            onNavigateToPricing = { product: Product, saleType: SaleType -> }
        )
         */
    }
}