package io.github.vbelles.billboard.ui.screens.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.vbelles.billboard.R
import io.github.vbelles.billboard.ui.components.NodeComponent
import io.github.vbelles.billboard.ui.components.PlaceholderNode

@Composable
fun StripComponent(strip: StripState, action: (PageAction) -> Unit) {
    Column {
        Row {
            Text(
                text = strip.title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.strip_more),
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .clickable { action(PageAction.ViewMoreClicked(strip.source, strip.contentType)) }
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
                items(strip.contents) { content ->
                    NodeComponent(content) { action(PageAction.ContentClicked(content.id, content.type)) }
                }
            }
        }
    }
}
