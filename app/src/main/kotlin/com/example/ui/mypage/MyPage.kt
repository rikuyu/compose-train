package com.example.ui.mypage

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.utils.Routes

fun NavGraphBuilder.addMyPage() {
    composable(route = Routes.MyPage.route) {
        MyPage()
    }
}

@Composable
fun MyPage() {

}