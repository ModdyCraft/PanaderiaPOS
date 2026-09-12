package com.moddy.panaderiapos.feature.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    companion object {
        private const val MOCK_SELLER_ID = "V76722430"
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnSellerIdChanged -> {
                _uiState.update {
                    it.copy(
                        sellerId = event.id,
                        isError = false,
                        errorMessage = null
                    )
                }
            }

            is LoginEvent.OnLoginClicked -> {
                val currentId = _uiState.value.sellerId.trim()
                if (currentId == MOCK_SELLER_ID) {
                    _uiState.update { it.copy(isError = false, errorMessage = null) }
                    event.onSuccess()
                } else {
                    _uiState.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Identificador no válido. Prueba con $MOCK_SELLER_ID"
                        )
                    }
                }
            }
        }
    }
}