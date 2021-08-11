package io.github.vbelles.billboard.ui.screens.grid

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import io.github.vbelles.billboard.data.model.ContentType
import io.github.vbelles.billboard.ui.components.NodeComponent
import io.github.vbelles.billboard.ui.components.PlaceholderNode
import io.github.vbelles.billboard.ui.components.Toolbar
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun GridScreen(navController: NavController, source: String, contentType: ContentType) {
    val viewModel: GridViewModel = getViewModel { parametersOf(source, contentType) }
    val state by viewModel.state.collectAsState()
    GridScreen(state, { navController.popBackStack() }) { lastVisibleIndex ->
        viewModel.loadMore(lastVisibleIndex)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridScreen(state: GridState, onBack: () -> Unit, loadMore: (Int) -> Unit) {
    val gridState = rememberLazyListState()
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val columns = if (landscape) 6 else 3
    val lastVisibleIndex = (gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) * columns
    LaunchedEffect(lastVisibleIndex) {
        loadMore(lastVisibleIndex)
    }
    LazyVerticalGrid(
        state = gridState,
        cells = GridCells.Fixed(columns),
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            additionalTop = 70.dp
        )
    ) {
        if (state.isLoading && state.contents.isEmpty()) {
            items(20) { PlaceholderNode() }
        }
        items(state.contents) { content ->
            NodeComponent(content) {}
        }
    }
    Toolbar(title = "View More", alpha = 0.8f, onBack)
}