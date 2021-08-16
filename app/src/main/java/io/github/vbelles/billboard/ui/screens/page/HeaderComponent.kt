package io.github.vbelles.billboard.ui.screens.page

import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.memory.MemoryCache
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import io.github.vbelles.billboard.data.model.Content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeaderComponent(strip: StripState, action: (PageAction) -> Unit) {
    val pages = if (strip.isLoading) 1 else strip.contents.size
    val pagerState = rememberPagerState(pageCount = pages, initialOffscreenLimit = 2, infiniteLoop = true)
    val colors = remember(strip.contents) { strip.contents.map { Color.Transparent }.toMutableStateList() }
    val color by animateColorAsState(colors.getOrElse(pagerState.currentPage) { Color.Transparent }, tween(600))
    val gradient = Brush.verticalGradient(0f to color.copy(alpha = color.alpha * 0.5f), 0.5f to Color.Transparent)

    HorizontalPager(
        state = pagerState, itemSpacing = 8.dp, modifier = Modifier
            .fillMaxWidth()
            .background(gradient)
            .statusBarsPadding()
            .padding(top = 80.dp)
    ) { page ->
        val content = strip.contents.getOrNull(page)
        if (content == null) {
            PlaceholderHeaderPage()
        } else {
            HeaderPage(content, { action(PageAction.ContentClicked(content.id, content.type)) }) { color ->
                colors[page] = color
            }
        }
    }

}

@Composable
fun HeaderPage(content: Content, onClick: (Content) -> Unit, onColorObtained: (Color) -> Unit) {
    val loader = LocalImageLoader.current
    var key: MemoryCache.Key? by remember { mutableStateOf(null) }
    val backgroundColor = MaterialTheme.colors.background
    LaunchedEffect(key) {
        val color = getHeaderColor(key?.let { loader.memoryCache[it] }, backgroundColor)
        onColorObtained(color)
    }
    val painter = rememberImagePainter(content.posterPath) {
        crossfade(true)
        listener(onSuccess = { _, metadata -> key = metadata.memoryCacheKey })
    }

    BoxWithConstraints {
        Card(shape = RoundedCornerShape(16.dp)) {
            Image(
                painter = painter,
                contentDescription = content.title,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(2 / 3f)
                    .clickable { onClick(content) },
            )
        }
    }
}

private suspend fun getHeaderColor(bitmap: Bitmap?, defaultColor: Color): Color = withContext(Dispatchers.Default) {
    bitmap ?: return@withContext Color.Transparent
    val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
    val palette = Palette.from(mutableBitmap).generate()
    val minLuminance = defaultColor.luminance()
    val vibrant = Color(palette.getVibrantColor(android.graphics.Color.TRANSPARENT))
    if (vibrant.luminance() < minLuminance) {
        val lightVibrant = Color(palette.getLightVibrantColor(defaultColor.value.toInt()))
        if (lightVibrant.luminance() < minLuminance) Color.Transparent else lightVibrant
    } else vibrant
}


@Composable
fun PlaceholderHeaderPage() {
    Card(shape = RoundedCornerShape(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(2 / 3f)
                .placeholder(visible = true, highlight = PlaceholderHighlight.shimmer()),
        )
    }
}