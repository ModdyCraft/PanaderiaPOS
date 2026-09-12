package com.moddy.panaderiapos.feature.auth

data class LoginUiState(
    val sellerId: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

sealed interface LoginEvent {
    data class OnSellerIdChanged(val id: String) : LoginEvent
    data class OnLoginClicked(val onSuccess: () -> Unit) : LoginEvent
}