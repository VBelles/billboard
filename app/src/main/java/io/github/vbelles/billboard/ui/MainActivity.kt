package io.github.vbelles.billboard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import io.github.vbelles.billboard.ui.screens.main.MainScreen
import io.github.vbelles.billboard.ui.theme.BillboardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BillboardTheme {
                ProvideWindowInsets {
                    Surface(color = MaterialTheme.colors.background) {
                        MainScreen()
                    }
                }
            }
        }
    }
}