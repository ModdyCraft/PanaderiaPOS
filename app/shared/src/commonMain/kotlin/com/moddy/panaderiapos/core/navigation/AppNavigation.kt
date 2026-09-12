package com.moddy.panaderiapos.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moddy.panaderiapos.feature.auth.LoginScreen
import com.moddy.panaderiapos.feature.auth.LoginViewModel
import com.moddy.panaderiapos.feature.cart.CartScreen
import com.moddy.panaderiapos.feature.cart.CartViewModel
import com.moddy.panaderiapos.feature.catalog.ProductCatalogScreen
import com.moddy.panaderiapos.feature.catalog.ProductCatalogViewModel
import com.moddy.panaderiapos.feature.history.SalesHistoryScreen
import com.moddy.panaderiapos.feature.history.SalesHistoryViewModel
import com.moddy.panaderiapos.feature.pricing.PricingCalculatorScreen
import com.moddy.panaderiapos.feature.pricing.PricingCalculatorViewModel
import com.moddy.panaderiapos.models.PricingTier
import com.moddy.panaderiapos.models.Product
import com.moddy.panaderiapos.navigation.NavRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Instancias de ViewModels (pueden reemplazarse con inyección de Koin)
    val loginViewModel = remember { LoginViewModel() }
    val catalogViewModel = remember { ProductCatalogViewModel() }
    val pricingViewModel = remember { PricingCalculatorViewModel() }
    val cartViewModel = remember { CartViewModel() }
    val historyViewModel = remember { SalesHistoryViewModel() }

    // Productos Mock iniciales para probar la app
    remember {
        catalogViewModel.setProducts(
            listOf(
                Product("1", "Mantequilla Manty", "Abarrotes", null, PricingTier(1, 5.80)),
                Product("2", "Pan Frances", "Panes", PricingTier(5, 1.00), PricingTier(4, 1.00)),
                Product("3", "Bizcocho con Crema", "Pastelería", PricingTier(3, 1.00), PricingTier(2, 1.00)),
                Product("4", "Budin", "Pastelería", PricingTier(2, 1.50), PricingTier(1, 1.00))
            )
        )
    }

    NavHost(
        navController = navController,
        startDestination = NavRoute.Login
    ) {
        // --- 1. LOGIN ---
        composable<NavRoute.Login> {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(NavRoute.Catalog) {
                        popUpTo(NavRoute.Login) { inclusive = true }
                    }
                }
            )
        }

        // --- 2. CATÁLOGO ---
        composable<NavRoute.Catalog> {
            ProductCatalogScreen(
                viewModel = catalogViewModel,
                onNavigateToPricing = { product, saleType ->
                    pricingViewModel.setupProduct(product, saleType)
                    // Navegación directa o mediante overlay
                },
                onNavigateToCart = {
                    navController.navigate(NavRoute.Cart)
                },
                onNavigateToHistory = {
                    navController.navigate(NavRoute.History)
                }
            )
        }

        // --- 3. CARRITO ---
        composable<NavRoute.Cart> {
            CartScreen(
                viewModel = cartViewModel,
                onNavigateToCatalog = {
                    navController.navigate(NavRoute.Catalog) {
                        popUpTo(NavRoute.Catalog) { inclusive = true }
                    }
                },
                onNavigateToHistory = {
                    navController.navigate(NavRoute.History) {
                        popUpTo(NavRoute.Catalog) { inclusive = false }
                    }
                }
            )
        }

        // --- 4. HISTORIAL ---
        composable<NavRoute.History> {
            SalesHistoryScreen(
                viewModel = historyViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}