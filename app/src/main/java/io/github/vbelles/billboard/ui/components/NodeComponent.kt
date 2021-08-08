package io.github.vbelles.billboard.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import io.github.vbelles.billboard.data.model.Content

private val NodeCorner = 18.dp
private val NodeWidth = 120.dp
private val NodeHeight = 180.dp

@Composable
fun NodeComponent(content: Content) {
    Card(shape = RoundedCornerShape(NodeCorner)) {
        Image(
            painter = rememberImagePainter(content.posterPath) { crossfade(true) },
            contentDescription = content.title,
            modifier = Modifier.size(NodeWidth, NodeHeight),
        )
    }
}

@Composable
fun PlaceholderNode() {
    Card(shape = RoundedCornerShape(NodeCorner)) {
        Box(
            modifier = Modifier
                .size(NodeWidth, NodeHeight)
                .placeholder(visible = true, highlight = PlaceholderHighlight.shimmer()),
        )
    }

}