package com.example.ui.catalog

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.utils.Routes

fun NavGraphBuilder.addCatalog() {
    composable(route = Routes.Catalog.route) {
        Catalog()
    }
}

@Composable
fun Catalog() {
}
