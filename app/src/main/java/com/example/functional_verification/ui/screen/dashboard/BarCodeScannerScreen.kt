package com.example.functional_verification.ui.screen.dashboard

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.runtime.Composable
import com.example.functional_verification.ui.components.BarCodeScanner

@OptIn(ExperimentalGetImage::class)
@Composable
fun BarCodeScannerScreen() {
    BarCodeScanner()
}