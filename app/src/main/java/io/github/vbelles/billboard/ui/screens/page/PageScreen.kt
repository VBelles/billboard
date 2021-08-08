package io.github.vbelles.billboard.ui.screens.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.vbelles.billboard.data.model.Section
import io.github.vbelles.billboard.ui.components.NodeComponent
import io.github.vbelles.billboard.ui.components.PlaceholderNode
import io.github.vbelles.billboard.ui.components.Toolbar
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.min

@Composable
fun PageScreen(navController: NavController, section: Section) {
    val viewModel: PageViewModel = getViewModel { parametersOf(section) }
    val pageState by viewModel.state.collectAsState()
    PageScreen(pageState) { source ->
        viewModel.loadStrip(source)
    }
}

@Composable
fun PageScreen(pageState: PageState, onLoad: (String) -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState, contentPadding = PaddingValues(vertical = 56.dp)) {
        items(pageState.strips) { strip ->
            Spacer(modifier = Modifier.size(20.dp))
            StripComponent(strip, onLoad)
        }
    }
    var alpha = 0.8f
    if (listState.firstVisibleItemIndex == 0) {
        alpha = min(alpha, listState.firstVisibleItemScrollOffset / 200f)
    }
    Toolbar(title = pageState.title, alpha = alpha)
}

@Composable
fun StripComponent(strip: StripState, onLoad: (String) -> Unit) {
    LaunchedEffect(strip.source) {
        onLoad(strip.source)
    }
    Column {
        Row {
            Text(
                text = strip.title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "View more",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .clickable { }
                    .padding(4.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(
            horizontalArrangement = spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            if (strip.isLoading) {
                items(4) { PlaceholderNode() }
            } else {
                items(strip.contents) { content -> NodeComponent(content) }
            }
        }
    }

}
