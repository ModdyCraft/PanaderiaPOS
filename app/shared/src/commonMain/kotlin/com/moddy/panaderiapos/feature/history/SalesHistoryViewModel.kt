package com.moddy.panaderiapos.feature.history

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SalesHistoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SalesHistoryUiState())
    val uiState: StateFlow<SalesHistoryUiState> = _uiState.asStateFlow()

    fun addSaleRecord(record: SaleRecord) {
        _uiState.update { current ->
            current.copy(records = listOf(record) + current.records)
        }
    }

    fun onEvent(
        event: SalesHistoryEvent,
        onNavigateBack: () -> Unit = {}
    ) {
        when (event) {
            is SalesHistoryEvent.OnRecordClicked -> {
                _uiState.update { it.copy(selectedRecordForDetail = event.record) }
            }
            SalesHistoryEvent.OnDismissDetailDialog -> {
                _uiState.update { it.copy(selectedRecordForDetail = null) }
            }
            SalesHistoryEvent.OnBackClicked -> {
                onNavigateBack()
            }
        }
    }
}