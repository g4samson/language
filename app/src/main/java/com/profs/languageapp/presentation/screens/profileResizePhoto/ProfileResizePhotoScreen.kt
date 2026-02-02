package com.profs.languageapp.presentation.screens.profileResizePhoto

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.profs.languageapp.R
import com.profs.languageapp.presentation.composable.CropImageComponent
import com.profs.languageapp.presentation.screens.profile.ProfileViewModel
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileResizePhotoScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel
) {
    val uri by viewModel.selectedImageUri.collectAsState()
    val context = LocalContext.current

    if (uri == null) return

    val bitmap by remember(uri) {
        mutableStateOf(decodeImage(context, uri!!))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                {
                    Box(
                        modifier = Modifier
                            .fillMaxSize().padding(start = 8.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Column {
                            Text(
                                stringResource(R.string.profile_resize_title),
                                style = Typography.titleLarge.copy(color = DefaultWhite)
                            )

                            Spacer(modifier = Modifier.height(17.dp))
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(104.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlue)
            )
        }
    ) { innerPadding ->

        CropImageComponent(
            bitmap = bitmap,
            onSave = {
                navController.popBackStack()
            },
            onCancel = {
                navController.popBackStack()
            }, modifier = Modifier.padding(innerPadding)
        )
    }
}

fun decodeImage(context: Context, uri: Uri): ImageBitmap {
    return if (Build.VERSION.SDK_INT >= 28) {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source).asImageBitmap()
    } else {
        context.contentResolver.openInputStream(uri)!!.use {
            BitmapFactory.decodeStream(it)
        }.asImageBitmap()
    }
}