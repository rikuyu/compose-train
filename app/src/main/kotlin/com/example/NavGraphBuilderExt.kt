package com.example

import Routes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.feature.catalog.CatalogScreen
import com.example.rickandmorty.grid.GridScreen
import com.google.firebase.auth.FirebaseUser

fun NavGraphBuilder.addGrid(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
) {
    composable(route = Routes.Grid.route) {
        GridScreen(
            scaffoldState = scaffoldState,
            onClickItem = {
                navController.navigate(
                    Routes.DetailCharacter.createRoute(it.id),
                )
            },
        )
    }
}

fun NavGraphBuilder.addFavorite(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
) {
    composable(route = Routes.Favorite.route) {
        com.example.rickandmorty.favorite.FavoriteScreen(
            scaffoldState = scaffoldState,
            navController = navController,
        )
    }
}

fun NavGraphBuilder.addDetail(scaffoldState: ScaffoldState) {
    composable(
        route = "${Routes.DetailCharacter.route}/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
                nullable = false
            },
        ),
    ) { backStackEntry ->
        com.example.rickandmorty.detail.DetailScreen(
            backStackEntry.arguments?.getInt("id") ?: 0,
            scaffoldState,
        )
    }
}

fun NavGraphBuilder.addLogIn(navController: NavHostController) {
    composable(route = Routes.LogIn.route) {
        com.example.todo.TodoLogInScreen(navController)
    }
}

fun NavGraphBuilder.addAddTodo(navController: NavController) {
    composable(route = Routes.CreateTodo.route) {
        com.example.todo.AddTodoScreen(modifier = Modifier.padding(4.dp), navController)
    }
}

fun NavGraphBuilder.addTodo(
    navController: NavController,
    firebaseUser: FirebaseUser?,
) {
    composable(route = Routes.Todo.route) {
        com.example.todo.TodoScreen(navController, firebaseUser)
    }
}

fun NavGraphBuilder.addUpdateTodo(navController: NavController) {
    composable(
        route = "${Routes.UpdateTodo.route}/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
                nullable = false
            },
        ),
    ) {
        com.example.todo.UpdateTodoScreen(
            modifier = Modifier.padding(4.dp),
            navController = navController,
            id = it.arguments?.getString("id"),
        )
    }
}

fun NavGraphBuilder.addSignUp(navController: NavHostController) {
    composable(route = Routes.SignUp.route) {
        com.example.todo.TodoSignUpScreen(navController)
    }
}

fun NavGraphBuilder.addCatalog(
    openScoreBoard: () -> Unit,
    openDraggableBox: () -> Unit,
    openThreadsCard: () -> Unit,
) {
    composable(route = Routes.Catalog.route) {
        CatalogScreen(
            openScoreBoard = openScoreBoard,
            openDraggableBox = openDraggableBox,
            openThreadsCard = openThreadsCard,
        )
    }
}
