/**
 * File Name: CameraPreview.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2025/04/10
 * Copyright: © 2025 Gyoushin Ou. All rights reserved.
 * Description: CameraPreview設置
 */

package com.example.rennshukun_compose.ui.components.bar_code_scanner

import android.content.Context
import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

fun setupCameraPreview(
    previewView: PreviewView,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    onBarcodeScanned: (String) -> Unit, // Callback for scanning
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build()
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        val barcodeAnalyzer = BarcodeAnalyzer(onBarcodeScanned)

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context), barcodeAnalyzer)

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        try {
            cameraProvider.unbindAll() // Unbind use cases before rebinding
            preview.setSurfaceProvider(previewView.surfaceProvider) // Set the surface provider for the preview
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis
            )
            // Get the CameraControl to manipulate camera settings
            val cameraControl = camera.cameraControl
            //cameraControl.enableTorch(true)
            // Attempt to lock focus (this is not a direct method, but you can use focus distance)
            cameraControl.setLinearZoom(0f) // This is not focus lock, but can help in some cases.

        } catch (exc: Exception) {
            Log.e("CameraX", "Use case binding failed", exc)
        }
    }, ContextCompat.getMainExecutor(context))
}