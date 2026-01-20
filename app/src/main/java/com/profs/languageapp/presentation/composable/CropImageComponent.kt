package com.profs.languageapp.presentation.composable

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CropImageComponent(
    bitmap: ImageBitmap,
    modifier: Modifier = Modifier,
    cropSizeDp: Dp = 260.dp,
    onSave: (Bitmap) -> Unit,
    onCancel: () -> Unit
) {
    val density = LocalDensity.current
    val cropSizePx = with(density) { cropSizeDp.toPx() }

    var cropCenter by remember {
        mutableStateOf(
            Offset(
                bitmap.width / 2f,
                bitmap.height / 2f
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // Image
        Image(
            bitmap = bitmap,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        // Overlay
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        cropCenter += dragAmount
                    }
                }
                .graphicsLayer {
                    compositingStrategy =
                        CompositingStrategy.Offscreen
                }
        ) {
            // dark background
            drawRect(
                color = Color.Black.copy(alpha = 0.6f)
            )

            // transparent circle
            drawCircle(
                color = Color.Transparent,
                radius = cropSizePx / 2,
                center = cropCenter,
                blendMode = BlendMode.Clear
            )

            // square border
            drawRect(
                color = Color.White,
                topLeft = Offset(
                    cropCenter.x - cropSizePx / 2,
                    cropCenter.y - cropSizePx / 2
                ),
                size = Size(cropSizePx, cropSizePx),
                style = Stroke(width = 3f)
            )
        }

        // Actions
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    val croppedBitmap = cropBitmapToCircle(
                        source = bitmap.asAndroidBitmap(),
                        center = cropCenter,
                        sizePx = cropSizePx
                    )
                    onSave(croppedBitmap)
                }
            ) {
                Text("Save")
            }
        }
    }
}

fun cropBitmapToCircle(
    source: Bitmap,
    center: Offset,
    sizePx: Float
): Bitmap {
    val left = (center.x - sizePx / 2).toInt()
    val top = (center.y - sizePx / 2).toInt()

    val square = Bitmap.createBitmap(
        source,
        left,
        top,
        sizePx.toInt(),
        sizePx.toInt()
    )

    val output = Bitmap.createBitmap(
        square.width,
        square.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(output)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle(
        square.width / 2f,
        square.height / 2f,
        square.width / 2f,
        paint
    )

    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(square, 0f, 0f, paint)

    return output
}
