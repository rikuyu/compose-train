package com.example

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature.catalog.CatalogScreen
import com.example.ui.utils.Routes

fun NavGraphBuilder.addCatalog() {
    composable(route = Routes.Catalog.route) {
        CatalogScreen()
    }
}
