package com.yongjin.musicplayer.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme

@Composable
fun EmptyUI(
    message: String,
    button: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = message)
            Button(onClick = onClick) {
                Text(text = button)
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun EmptyUIPreview() {
    MusicPlayerTheme {
        Surface {
            EmptyUI(
                modifier = Modifier.fillMaxSize(),
                message = "음악를 들으려면 미디어 권한이 필요해요.",
                button = "권한 허용하기",
                onClick = {}
            )
        }
    }
}