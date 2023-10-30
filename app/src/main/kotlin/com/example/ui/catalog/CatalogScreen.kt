package com.example.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.utils.Routes
import com.example.ui.utils.showToast

fun NavGraphBuilder.addCatalog() {
    composable(route = Routes.Catalog.route) {
        CatalogScreen()
    }
}

@Composable
fun CatalogScreen() {
    val context = LocalContext.current
    CatalogContent {
        item { Divider(color = MaterialTheme.colorScheme.onBackground) }

        catalogItem(label = "label 1") {
            context.showToast("label 1")
        }
        catalogItem(label = "label 2") {
            context.showToast("label 2")
        }
        catalogItem(label = "label 3") {
            context.showToast("label 3")
        }
    }
}

@Composable
fun CatalogContent(content: LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        content()
    }
}
