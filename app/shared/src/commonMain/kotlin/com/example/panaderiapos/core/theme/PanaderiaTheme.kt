package com.example.panaderiapos.core.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PanaderiaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PanaderiaColorScheme
    ) {
        // Box global que dibuja el degradado de esquina a esquina en toda la app
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGradient)
        ) {
            content()
        }
    }
}