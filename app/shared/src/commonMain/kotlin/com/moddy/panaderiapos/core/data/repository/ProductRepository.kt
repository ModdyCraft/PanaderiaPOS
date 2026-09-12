package com.moddy.panaderiapos.core.data.repository

import com.moddy.panaderiapos.core.data.network.ApiService
import com.moddy.panaderiapos.feature.history.SaleRecord
import com.moddy.panaderiapos.models.Product

class ProductRepository(private val apiService: ApiService) {

    suspend fun getProducts(): Result<List<Product>> {
        return runCatching { apiService.fetchProducts() }
    }
}

class SalesRepository(private val apiService: ApiService) {

    suspend fun saveSale(record: SaleRecord): Result<Boolean> {
        return runCatching { apiService.postSaleRecord(record) }
    }
}