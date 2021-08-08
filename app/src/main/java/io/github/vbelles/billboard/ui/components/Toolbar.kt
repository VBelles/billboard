package io.github.vbelles.billboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun Toolbar(title: String, alpha: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface.copy(alpha = alpha))
            .statusBarsPadding()
            .padding(vertical = 10.dp)
    ) {
        Text(style = MaterialTheme.typography.h5, text = title, modifier = Modifier.align(Alignment.Center))
    }
}