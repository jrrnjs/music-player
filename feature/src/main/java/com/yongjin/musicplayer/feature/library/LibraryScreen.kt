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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.yongjin.musicplayer.designsystem.theme.MusicPlayerTheme
import com.yongjin.musicplayer.model.Album
import kotlinx.collections.immutable.ImmutableList

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen() {
    val permission = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_EXTERNAL_STORAGE
    } else {
        Manifest.permission.READ_MEDIA_AUDIO
    }
    val permissionState = rememberPermissionState(permission)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("라이브러리") })
        }
    ) {
        when {
            permissionState.status.isGranted -> LibraryScreen(
                modifier = Modifier.padding(it),
                albums = dummies
            )

            else -> PermissionDeniedScreen(
                modifier = Modifier.padding(it)
            )
        }
    }

    LaunchedEffect(Unit) {
        if (!permissionState.status.shouldShowRationale) {
            permissionState.launchPermissionRequest()
        }
    }
}

@Composable
private fun LibraryScreen(
    albums: ImmutableList<Album>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        columns = GridCells.Adaptive(160.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = albums,
            key = { it.id }
        ) { album ->
            AlbumItem(album)
        }
    }
}

@Composable
private fun PermissionDeniedScreen(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Box(
        modifier = modifier.fillMaxSize(),
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


private fun Context.startSettingsActivity() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .setData(Uri.parse("package:${packageName}"))
    startActivity(intent)
}


@Composable
@PreviewLightDark
@Preview(name = "Fold", device = Devices.PIXEL_FOLD)
@Preview(name = "Tablet", device = Devices.PIXEL_TABLET)
private fun LibraryScreenPreview() {
    MusicPlayerTheme {
        Surface {
            LibraryScreen(
                albums = dummies
            )
        }
    }
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