package io.github.vbelles.billboard.ui.screens.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import io.github.vbelles.billboard.data.model.ContentType
import io.github.vbelles.billboard.ui.components.Toolbar
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(navController: NavController, id: Int, contentType: ContentType) {
    val viewModel: DetailsViewModel = getViewModel { parametersOf(id, contentType) }
    val state by viewModel.state.collectAsState()
    DetailsScreen(state) {

    }
}

@Composable
fun DetailsScreen(state: DetailsState, action: (DetailsAction) -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .verticalScroll(scrollState)
            .navigationBarsPadding()) {
        Box {
            Image(
                painter = rememberImagePainter(state.backdrop),
                contentDescription = state.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
            )
            // Top protector gradient
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(Brush.verticalGradient(listOf(Color.Black.copy(alpha = 0.8f), Color.Transparent)))
            )
            // Bottom fading gradient
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .align(Alignment.BottomCenter)
                    .background(Brush.verticalGradient(listOf(Color.Transparent, MaterialTheme.colors.surface)))
            )
            Text(
                text = state.title.orEmpty(),
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.BottomCenter)
            )
        }

        Button(
            onClick = {

            },
            colors = buttonColors(backgroundColor = MaterialTheme.colors.secondary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Icon(painter = rememberVectorPainter(image = Icons.Rounded.PlayArrow), contentDescription = "Play")
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Watch Trailer")
        }
        Spacer(modifier = Modifier.size(10.dp))


        var maxLines by rememberSaveable { mutableStateOf(3) }
        Text(
            text = state.description.orEmpty(),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .animateContentSize()
                .clickable { maxLines = if (maxLines == 3) Int.MAX_VALUE else 3 }
                .padding(horizontal = 20.dp)
        )

    }
    Toolbar(title = "", alpha = 0.0f) { action(DetailsAction.BackClicked) }

}