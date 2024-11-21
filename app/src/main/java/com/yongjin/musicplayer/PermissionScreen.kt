package com.yongjin.musicplayer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.yongjin.musicplayer.designsystem.EmptyUI
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme

@Composable
fun PermissionDeniedScreen() {
    val context = LocalContext.current
    Scaffold {
        EmptyUI(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            message = "음악를 들으려면 미디어 권한이 필요해요.",
            button = "권한 허용하기",
            onClick = { context.startSettingsActivity() }
        )
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