package com.example.rennshukun_compose.ui.screen.barCodeScanner

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.runtime.Composable
import com.example.rennshukun_compose.ui.components.BarCodeScanner

@OptIn(ExperimentalGetImage::class)
@Composable
fun BarCodeScannerScreen() {
    BarCodeScanner()
}