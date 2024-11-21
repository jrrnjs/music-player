package com.yongjin.musicplayer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme

@Composable
fun PermissionDeniedScreen() {
    val context = LocalContext.current
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("음악를 들으려면 미디어 권한이 필요해요.")
                Button(
                    onClick = { context.startSettingsActivity() }
                ) {
                    Text("권한 허용하기")
                }
            }
        }
    }
}

private fun Context.startSettingsActivity() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .setData(Uri.parse("package:${packageName}"))
    startActivity(intent)
}

@Composable
@PreviewLightDark
private fun PermissionDeniedScreenPreview() {
    MusicPlayerTheme {
        Surface {
            PermissionDeniedScreen()
        }
    }
}