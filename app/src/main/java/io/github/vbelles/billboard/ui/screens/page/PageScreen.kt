package io.github.vbelles.billboard.ui.screens.page

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.lazy.LazyColumn
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
import io.github.vbelles.billboard.ui.Screen
import io.github.vbelles.billboard.ui.components.Toolbar
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.min

@Composable
fun PageScreen(navController: NavController, title: String, sectionId: String) {
    val viewModel: PageViewModel = getViewModel { parametersOf(sectionId, title) }
    val pageState by viewModel.state.collectAsState()
    PageScreen(pageState) { action ->
        when (action) {
            is PageAction.ContentClicked ->
                navController.navigate(Screen.Details.parametrized(action.contentId, action.contentType))
            is PageAction.StripDisplayed ->
                viewModel.loadStrip(action.stripState)
            is PageAction.ViewMoreClicked ->
                navController.navigate(Screen.Grid.parametrized(action.source, action.contentType))
        }
    }
}

@Composable
fun PageScreen(pageState: PageState, action: (PageAction) -> Unit) {
    val listState = rememberLazyListState()
    BoxWithConstraints {
        val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
        val headers = if (isPortrait) 1 else 0
        LazyColumn(
            state = listState,
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = !isPortrait,
                additionalTop = if (isPortrait) 0.dp else 80.dp,
                additionalBottom = 80.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pageState.strips.take(headers), { strip -> strip.source }) { strip ->
                LaunchedEffect(strip.source) {
                    action(PageAction.StripDisplayed(strip))
                }
                HeaderComponent(strip, action)
            }
            items(pageState.strips.drop(headers), { strip -> strip.source }) { strip ->
                LaunchedEffect(strip.source) {
                    action(PageAction.StripDisplayed(strip))
                }
                StripComponent(strip, action)
            }
        }
        var alpha = 0.8f
        if (listState.firstVisibleItemIndex == 0) {
            alpha = min(alpha, listState.firstVisibleItemScrollOffset / 200f)
        }
        Toolbar(title = pageState.title, alpha = alpha)
    }
}
