package io.github.vbelles.billboard.ui.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import io.github.vbelles.billboard.data.model.Node
import io.github.vbelles.billboard.data.model.Strip


private val mockStrips = List(10) { strip ->
    Strip(title = "Strip $strip", subtitle = "", nodes = List(10) { node ->
        val id = strip * 100 + node + 1
        Node(
            id = "$id",
            title = "Node $id",
            poster = "https://picsum.photos/seed/$id/200/300"
        )
    })
}

@Preview
@Composable
fun MoviesScreenPreview() {
    MoviesScreen(mockStrips)
}

@Composable
fun MoviesScreen(navController: NavController) {
    MoviesScreen(mockStrips)
}

@Composable
fun MoviesScreen(strips: List<Strip>) {
    LazyColumn {
        items(strips) { strip ->
            Spacer(modifier = Modifier.size(20.dp))
            StripComponent(strip)
        }
    }
}

@Composable
fun StripComponent(strip: Strip) {
    Column {
        Text(text = strip.title, Modifier.padding(horizontal = 20.dp))
        if (strip.subtitle.isNotBlank()) {
            Text(text = strip.subtitle)
        }
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(strip.nodes) { node ->
                Spacer(modifier = Modifier.size(10.dp))
                NodeComponent(node)
            }
        }
    }

}

@Composable
fun NodeComponent(node: Node) {
    Card(shape = RoundedCornerShape(18.dp)) {
        Image(
            painter = rememberImagePainter(node.poster) { crossfade(true) },
            contentDescription = node.title,
            modifier = Modifier.size(120.dp, 180.dp)
        )
    }
}

