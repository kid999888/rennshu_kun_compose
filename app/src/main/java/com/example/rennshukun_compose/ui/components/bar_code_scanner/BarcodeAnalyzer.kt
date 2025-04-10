/**
 * File Name: BarcodeAnalyzer.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2025/04/10
 * Copyright: © 2025 Gyoushin Ou. All rights reserved.
 * Description: BarcodeAnalyzer
 */
package com.example.rennshukun_compose.ui.components.bar_code_scanner

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(
    private val onBarcodeScanned: (String) -> Unit,
    private val cooldownTimeMillis: Long = 2000L,
) : ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS) // or specify specific formats
            .build()
    )

    private var lastScanTime = 0L
    private var lastScannedValue: String? = null

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastScanTime < cooldownTimeMillis) {
            Log.d("BarcodeAnalyzer", "再スキャン、クールダウン時間が経過していません")
            imageProxy.close()
            return
        }

        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)

            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        barcodes.firstOrNull()?.rawValue?.let { value ->
                            // 只有当新扫描的值与上一次不同，或者距离上次扫描已经过去了足够长的时间，才回调
                            if (value != lastScannedValue || (currentTime - lastScanTime > cooldownTimeMillis)) {
                                lastScannedValue = value
                                lastScanTime = currentTime
                                onBarcodeScanned(value)
                                Log.d("BarcodeAnalyzer", "バーコードスキャン：${value}")
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("BarcodeAnalyzer", "バーコードスキャンエラー: ${e.message}")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } ?: imageProxy.close()
    }
}