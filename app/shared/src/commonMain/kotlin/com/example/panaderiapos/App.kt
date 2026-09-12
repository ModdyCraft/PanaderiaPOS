package com.example.panaderiapos

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.panaderiapos.core.theme.PanaderiaTheme
import com.example.panaderiapos.feature.auth.LoginScreen
import com.example.panaderiapos.feature.auth.LoginViewModel

@Suppress("ViewModelConstructorInComposable")
@Composable
@Preview
fun App() {
    PanaderiaTheme {
        LoginScreen(viewModel = LoginViewModel())
    }
}