package com.moddy.panaderiapos.core.data.network

import com.moddy.panaderiapos.feature.history.SaleRecord
import com.moddy.panaderiapos.models.Product
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class ApiService(
    private val client: HttpClient = HttpClient {
        install(ContentNegotiation) { json() }
    },
    private val baseUrl: String = "http://localhost:8080/api"
) {
    // GET: Obtener lista de productos
    suspend fun fetchProducts(): List<Product> {
        return client.get("$baseUrl/products").body()
    }

    // POST: Guardar registro de venta
    suspend fun postSaleRecord(record: SaleRecord): Boolean {
        val response = client.post("$baseUrl/sales") {
            contentType(ContentType.Application.Json)
            setBody(record)
        }
        return response.status == HttpStatusCode.Created || response.status == HttpStatusCode.OK
    }
}