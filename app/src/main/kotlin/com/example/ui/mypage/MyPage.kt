package com.example.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch
import com.example.ui.utils.Routes

fun NavGraphBuilder.addMyPage(
    navController: NavController,
    changeScreen: () -> Unit,
) {
    composable(route = Routes.MyPage.route) {
        changeScreen()
        MyPage(MyPageTab.list)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyPage(tabs: List<MyPageTab>) {
    Column {
        val pagerState = rememberPagerState(initialPage = 0)
        val coroutineScope = rememberCoroutineScope()
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = MaterialTheme.colors.primary,
                )
            },
            backgroundColor = MaterialTheme.colors.surface,
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = {
                        Text(text = stringResource(id = tab.titleRes))
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            // 現在のタブの位置から選択されたタブまでの移動量を計算してスクロールさせる
                            if (pagerState.currentPage > index) {
                                pagerState.animateScrollToPage(pagerState.currentPage - (pagerState.currentPage - index))
                            } else if (pagerState.currentPage < index) {
                                pagerState.animateScrollToPage(pagerState.currentPage + (index - pagerState.currentPage))
                            }
                        }
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onSurface,
                )
            }
        }
        HorizontalPager(
            count = tabs.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f), // PullRefreshIndicator が TabRow の上に出てしまうことの対策
        ) { index ->
            tabs[pagerState.currentPage].Content()
        }
    }
}

@Composable
fun AAA() {
    LazyColumn {
        items(list) {
            Text(
                text = "$it's item",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
            )
            Divider()
        }
    }
}

@Composable
fun BBB() {
    LazyColumn {
        items(list) {
            Text(
                text = "$it's item",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
            )
            Divider()
        }
    }
}

@Composable
fun CCC() {
    LazyColumn {
        items(list) {
            Text(
                text = "$it's item",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
            )
            Divider()
        }
    }
}

val list = List(100) { it + 1 }