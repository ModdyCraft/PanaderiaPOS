package com.example.panaderiapos

import com.example.panaderiapos.models.PricingTier
import com.example.panaderiapos.models.Product
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // 1. Instalar soporte para JSON
    install(ContentNegotiation) {
        json()
    }

    // 2. Definir Rutas de la API
    routing {
        get("/") {
            call.respondText("Servidor POS Panadería en ejecución")
        }

        route("/api") {
            get("/products") {
                call.respond(MockProductRepository.products)
            }
        }
    }
}

// --- REPOSITORIO DE PRUEBA EN MEMORIA ---

object MockProductRepository {
    val products = listOf(
        Product(
            id = "prod-1",
            name = "Mantequilla Manty",
            category = "Abarrotes",
            wholesalePricing = null,
            retailPricing = PricingTier(unitQuantity = 1, price = 5.80)
        ),
        Product(
            id = "prod-2",
            name = "Pan Frances",
            category = "Panes",
            wholesalePricing = PricingTier(unitQuantity = 5, price = 1.00),
            retailPricing = PricingTier(unitQuantity = 4, price = 1.00)
        ),
        Product(
            id = "prod-3",
            name = "Bizcocho con Crema",
            category = "Pastelería",
            wholesalePricing = PricingTier(unitQuantity = 3, price = 1.00),
            retailPricing = PricingTier(unitQuantity = 2, price = 1.00)
        ),
        Product(
            id = "prod-4",
            name = "Budin",
            category = "Pastelería",
            wholesalePricing = PricingTier(unitQuantity = 2, price = 1.50),
            retailPricing = PricingTier(unitQuantity = 1, price = 1.00)
        )
    )
}