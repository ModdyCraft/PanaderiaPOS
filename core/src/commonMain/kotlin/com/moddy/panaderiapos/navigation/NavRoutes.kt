package com.moddy.panaderiapos.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {
    @Serializable
    data object Login : NavRoute

    @Serializable
    data object Catalog : NavRoute

    @Serializable
    data object Cart : NavRoute

    @Serializable
    data object History : NavRoute
}