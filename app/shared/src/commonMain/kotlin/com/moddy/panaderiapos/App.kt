package com.moddy.panaderiapos

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.moddy.panaderiapos.core.navigation.AppNavigation
import com.moddy.panaderiapos.core.theme.PanaderiaTheme
import com.moddy.panaderiapos.feature.cart.CartScreen
import com.moddy.panaderiapos.feature.cart.CartViewModel
import com.moddy.panaderiapos.feature.history.SalesHistoryScreen
import com.moddy.panaderiapos.feature.history.SalesHistoryViewModel
import com.moddy.panaderiapos.feature.pricing.PricingCalculatorScreen
import com.moddy.panaderiapos.feature.pricing.PricingCalculatorViewModel

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
        // CartScreen(viewModel = CartViewModel(), {}, {})
        /*
        PricingCalculatorScreen(
            viewModel = PricingCalculatorViewModel(),
            onItemAdded = {},
            onCancel = {},
        )
         */
        /*
        SalesHistoryScreen(
            viewModel = SalesHistoryViewModel(),
            onNavigateBack = {}
        )
         */
        AppNavigation()
    }
}