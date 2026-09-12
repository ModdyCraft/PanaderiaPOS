package com.example.panaderiapos.feature.auth

data class LoginUiState(
    val sellerId: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface LoginEvent {
    data class OnSellerIdChanged(val id: String) : LoginEvent
    data object OnLoginClicked : LoginEvent
}