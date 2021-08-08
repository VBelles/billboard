package io.github.vbelles.billboard.ui.screens.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import io.github.vbelles.billboard.data.model.Content
import io.github.vbelles.billboard.data.model.Section
import io.github.vbelles.billboard.ui.components.Toolbar
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

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
    val alpha = when (listState.firstVisibleItemIndex) {
        0 -> (listState.firstVisibleItemScrollOffset / 200f).coerceAtMost(0.7f)
        else -> 0.7f
    }
    Toolbar(title = pageState.title, alpha = alpha)
}

@Composable
fun StripComponent(strip: StripState, onLoad: (String) -> Unit) {
    LaunchedEffect(strip.source) {
        onLoad(strip.source)
    }
    Column {
        Text(
            text = strip.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 20.dp),
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            if (strip.isLoading) {
                items(4) {
                    Spacer(modifier = Modifier.size(10.dp))
                    NodeComponent(null)
                }
            } else {
                items(strip.contents) { content ->
                    Spacer(modifier = Modifier.size(10.dp))
                    NodeComponent(content)
                }
            }
        }
    }

}

@Composable
fun NodeComponent(content: Content?) {
    Card(shape = RoundedCornerShape(18.dp)) {
        Image(
            painter = rememberImagePainter(content?.posterPath) { crossfade(true) },
            contentDescription = content?.title,
            modifier = Modifier
                .size(120.dp, 180.dp)
                .placeholder(visible = content == null, highlight = PlaceholderHighlight.shimmer())
        )
    }
}
