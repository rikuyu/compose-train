package com.example.ui.mypage

import androidx.compose.runtime.Composable
import com.example.composetrainapp.R

enum class MyPageTab {
    AAA,
    BBB,
    CCC,

    ;

    val titleRes: Int
        get() = when (this) {
            AAA -> R.string.tab_a
            BBB -> R.string.tab_b
            CCC -> R.string.tab_c
        }

    @Composable
    fun Content(
        isSwipeRefreshEnabled: Boolean = false
    ) = when (this) {
        AAA -> AAA()
        BBB -> BBB()
        CCC -> CCC()
    }

    companion object {
        val list = values().toList()
    }
}