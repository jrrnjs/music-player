package com.yongjin.musicplayer

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme
import com.yongjin.musicplayer.feature.library.LibraryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicPlayerTheme {
                val permission = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                } else {
                    Manifest.permission.READ_MEDIA_AUDIO
                }
                val permissionState = rememberPermissionState(permission)

                when {
                    permissionState.status.isGranted -> LibraryScreen()
                    else -> PermissionDeniedScreen()
                }

                LaunchedEffect(Unit) {
                    if (!permissionState.status.shouldShowRationale) {
                        permissionState.launchPermissionRequest()
                    }
                }
            }
        }
    }
}