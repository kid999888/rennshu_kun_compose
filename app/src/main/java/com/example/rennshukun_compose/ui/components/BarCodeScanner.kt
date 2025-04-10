/**
 * File Name: BarCodeScanner.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2024/11/14
 * Copyright: © 2024 Gyoushin Ou. All rights reserved.
 * Description:
 */

package com.example.rennshukun_compose.ui.components

import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@OptIn(ExperimentalGetImage::class)
@Composable
fun BarCodeScanner() {
    var scannedValue by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = remember { Executors.newSingleThreadExecutor() }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(Color.Black.toArgb())
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                    implementationMode = PreviewView.ImplementationMode.PERFORMANCE
                }
            },
            modifier = Modifier.fillMaxSize(),
        ) { previewView ->
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(previewView.surfaceProvider)

                val imageAnalysis = ImageAnalysis.Builder()
                    .setResolutionSelector(
                        ResolutionSelector.Builder()
                            .setAspectRatioStrategy(
                                AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY
                            )
                            .build()
                    )
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(executor) { imageProxy ->
                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        val image = InputImage.fromMediaImage(
                            mediaImage,
                            imageProxy.imageInfo.rotationDegrees
                        )

                        val scanner = BarcodeScanning.getClient()
                        scanner.process(image)
                            .addOnSuccessListener { barcodes ->
                                for (barcode in barcodes) {
                                    barcode.rawValue?.let { value ->
                                        scannedValue = value
                                    }
                                }
                            }
                            .addOnCompleteListener {
                                imageProxy.close()
                            }
                    } else {
                        imageProxy.close()
                    }
                }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(context))
        }

        // 自定义扫描框 UI
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            val w = size.width
            val h = size.height
            val squareSize = w.coerceAtMost(h) * 0.75f
            val shiftTop = w / 8

            // 绘制遮罩
            val dimmingColor = Color.Black.copy(alpha = 0.3f)

            // 上方遮罩
            drawRect(
                color = dimmingColor,
                size = Size(w, (h - squareSize) / 2 - shiftTop),
                topLeft = Offset(x = 0f, y = 0f)
            )
            // 下方遮罩
            drawRect(
                color = dimmingColor,
                size = Size(w, (h - squareSize) / 2 + shiftTop),
                topLeft = Offset(x = 0f, y = h - (h - squareSize) / 2 - shiftTop)
            )
            // 左侧遮罩
            drawRect(
                color = dimmingColor,
                size = Size((w - squareSize) / 2, squareSize),
                topLeft = Offset(x = 0f, y = (h - squareSize) / 2 - shiftTop)
            )
            // 右侧遮罩
            drawRect(
                color = dimmingColor,
                size = Size((w - squareSize) / 2, squareSize),
                topLeft = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop)
            )

            // 绘制扫描框四角
            val cornerLength = 80f
            val cornerWidth = 10f
            val cornerColor = Color.White

            // 左上角
            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(
                    x = (w - squareSize) / 2 + cornerLength,
                    y = (h - squareSize) / 2 - shiftTop
                ),
                strokeWidth = cornerWidth,
                cap = StrokeCap.Round
            )
            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(
                    x = (w - squareSize) / 2,
                    y = (h - squareSize) / 2 - shiftTop + cornerLength
                ),
                strokeWidth = cornerWidth,
                cap = StrokeCap.Round
            )

            // 右上角
            drawLine(
                color = cornerColor,
                start = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(
                    x = w - (w - squareSize) / 2 - cornerLength,
                    y = (h - squareSize) / 2 - shiftTop
                ),
                strokeWidth = cornerWidth,
                cap = StrokeCap.Round
            )
            drawLine(
                color = cornerColor,
                start = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(
                    x = w - (w - squareSize) / 2,
                    y = (h - squareSize) / 2 - shiftTop + cornerLength
                ),
                strokeWidth = cornerWidth,
                cap = StrokeCap.Round
            )

            // 左下角
            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(
                    x = (w - squareSize) / 2 + cornerLength,
                    y = h - (h - squareSize) / 2 - shiftTop
                ),
                strokeWidth = cornerWidth,
                cap = StrokeCap.Round
            )
            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(
                    x = (w - squareSize) / 2,
                    y = h - (h - squareSize) / 2 - shiftTop - cornerLength
                ),
                strokeWidth = cornerWidth,
                cap = StrokeCap.Round
            )

            // 右下角
            drawLine(
                color = cornerColor,
                start = Offset(
                    x = w - (w - squareSize) / 2,
                    y = h - (h - squareSize) / 2 - shiftTop
                ),
                end = Offset(
                    x = w - (w - squareSize) / 2 - cornerLength,
                    y = h - (h - squareSize) / 2 - shiftTop
                ),
                strokeWidth = cornerWidth,
                cap = StrokeCap.Round
            )
            drawLine(
                color = cornerColor,
                start = Offset(
                    x = w - (w - squareSize) / 2,
                    y = h - (h - squareSize) / 2 - shiftTop
                ),
                end = Offset(
                    x = w - (w - squareSize) / 2,
                    y = h - (h - squareSize) / 2 - shiftTop - cornerLength
                ),
                strokeWidth = cornerWidth,
                cap = StrokeCap.Round
            )
        })

        scannedValue?.let { value ->
            Text(
                text = "扫描结果: $value",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                color = Color.White
            )
        }
    }
}