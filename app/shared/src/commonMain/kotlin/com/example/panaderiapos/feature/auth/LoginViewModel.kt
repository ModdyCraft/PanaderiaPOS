package com.example.panaderiapos.feature.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnSellerIdChanged -> {
                _uiState.update { it.copy(sellerId = event.id) }
            }
            LoginEvent.OnLoginClicked -> {
                // Por el momento no realiza acciones de navegación ni red
            }
        }
    }
}