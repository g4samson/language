package com.profs.languageapp.presentation.composable

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.profs.languageapp.R
import com.profs.languageapp.presentation.theme.AnotherColorThatIsNOTInList
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.Typography

@Composable
fun CropImageComponent(
    bitmap: ImageBitmap,
    onSave: (Bitmap) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var cropCenter by remember { mutableStateOf(Offset.Zero) }
    val cropRadius = 300f

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AnotherColorThatIsNOTInList)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = bitmap,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { canvasSize = it }
                .pointerInput(Unit) {
                    detectDragGestures { change, drag ->
                        change.consume()

                        val rect = calculateImageRect(canvasSize, bitmap)
                        cropCenter = (cropCenter + drag)
                            .coerceInImageBounds(rect, cropRadius)
                    }
                }
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
        ) {
            if (cropCenter == Offset.Zero && canvasSize != IntSize.Zero) {
                val rect = calculateImageRect(canvasSize, bitmap)
                cropCenter = Offset(
                    x = rect.left + rect.width / 2f,
                    y = rect.top + rect.height / 2f
                )
            }

            drawRect(Color.Black.copy(alpha = 0.6f))

            drawCircle(
                color = Color.Transparent,
                radius = cropRadius,
                center = cropCenter,
                blendMode = BlendMode.Clear
            )

            drawCircle(
                color = Color.White,
                radius = cropRadius,
                center = cropCenter,
                style = Stroke(3f)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp).padding(top = 15.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = stringResource(R.string.resize_photo_hint),
                style = Typography.titleLarge.copy(
                    color = DefaultWhite,
                    textAlign = TextAlign.Start
                )
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            DefaultButton(
                text = stringResource(R.string.use_that_image)
            ) {
                onSave(bitmap.asAndroidBitmap())
            }
        }
    }
}

private data class ImageRect(
    val left: Float,
    val top: Float,
    val width: Float,
    val height: Float
)

private fun calculateImageRect(
    canvasSize: IntSize,
    bitmap: ImageBitmap
): ImageRect {
    val canvasRatio = canvasSize.width.toFloat() / canvasSize.height
    val imageRatio = bitmap.width.toFloat() / bitmap.height

    return if (imageRatio > canvasRatio) {
        // fit by width
        val width = canvasSize.width.toFloat()
        val height = width / imageRatio
        val top = (canvasSize.height - height) / 2f

        ImageRect(
            left = 0f,
            top = top,
            width = width,
            height = height
        )
    } else {
        // fit by height
        val height = canvasSize.height.toFloat()
        val width = height * imageRatio
        val left = (canvasSize.width - width) / 2f

        ImageRect(
            left = left,
            top = 0f,
            width = width,
            height = height
        )
    }
}

private fun Offset.coerceInImageBounds(
    rect: ImageRect,
    radius: Float
): Offset {
    return Offset(
        x = x.coerceIn(
            rect.left + radius,
            rect.left + rect.width - radius
        ),
        y = y.coerceIn(
            rect.top + radius,
            rect.top + rect.height - radius
        )
    )
}