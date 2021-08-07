package io.github.vbelles.billboard.ui.screens.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import io.github.vbelles.billboard.data.model.Content
import io.github.vbelles.billboard.data.model.Section
import org.koin.androidx.compose.getViewModel

@Composable
fun PageScreen(navController: NavController, title: String?, section: Section) {
    val viewModel: PageViewModel = getViewModel()
    LaunchedEffect(viewModel) {
        viewModel.start(section.strips)
    }
    val pageState by viewModel.state.collectAsState()
    PageScreen(pageState) { source ->
        viewModel.loadStrip(source)
    }
}

@Composable
fun PageScreen(pageState: PageState, onLoad: (String) -> Unit) {
    LazyColumn {
        items(pageState.strips) { strip ->
            Spacer(modifier = Modifier.size(20.dp))
            StripComponent(strip, onLoad)
        }
    }
}

@Composable
fun StripComponent(strip: StripState, onLoad: (String) -> Unit) {
    LaunchedEffect(strip.source) {
        onLoad(strip.source)
    }
    Column {
        Text(text = strip.title, Modifier.padding(horizontal = 20.dp))
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(strip.contents) { content ->
                Spacer(modifier = Modifier.size(10.dp))
                NodeComponent(content)
            }
        }
    }

}

@Composable
fun NodeComponent(content: Content) {
    Card(shape = RoundedCornerShape(18.dp)) {
        Image(
            painter = rememberImagePainter(content.posterPath) { crossfade(true) },
            contentDescription = content.title,
            modifier = Modifier.size(120.dp, 180.dp)
        )
    }
}
