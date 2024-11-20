package com.yongjin.musicplayer.feature.library

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LibraryScreen() {
    val permission = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_EXTERNAL_STORAGE
    } else {
        Manifest.permission.READ_MEDIA_AUDIO
    }
    val permissionState = rememberPermissionState(permission)

    when {
        permissionState.status.isGranted -> LibraryScreen(Modifier)
        else -> PermissionDeniedScreen()
    }

    LaunchedEffect(Unit) {
        if (!permissionState.status.shouldShowRationale) {
            permissionState.launchPermissionRequest()
        }
    }
}

@Composable
private fun LibraryScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("동의")
        }
    }
}

@Composable
private fun PermissionDeniedScreen() {
    val context = LocalContext.current
    Scaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
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
private fun LibraryScreenPreview() {
    MusicPlayerTheme {
        LibraryScreen()
    }
}