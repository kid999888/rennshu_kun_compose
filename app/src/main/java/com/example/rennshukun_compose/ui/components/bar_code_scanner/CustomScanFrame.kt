/**
 * File Name: CustomScanFrame.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2025/04/10
 * Copyright: © 2025 Gyoushin Ou. All rights reserved.
 * Description: スキャンフレーム
 */

package com.example.rennshukun_compose.ui.components.bar_code_scanner

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun CustomScanFrame() {
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        val w = size.width
        val h = size.height
        val squareSize = w * 0.75f
        val shiftTop = w / 8

        // Draw dimming mask
        val dimmingColor = Color.Black.copy(alpha = 0.3f)

        // Top mask
        drawRect(
            color = dimmingColor,
            size = Size(w, shiftTop),
            topLeft = Offset(x = 0f, y = 0f)
        )
        // Bottom mask
        drawRect(
            color = dimmingColor,
            size = Size(w, (h - squareSize) / 2),
            topLeft = Offset(x = 0f, y = h - (h - squareSize) / 2)
        )
        // Left mask
        drawRect(
            color = dimmingColor,
            size = Size((w - squareSize) / 2, squareSize),
            topLeft = Offset(x = 0f, y = (h - squareSize) / 2)
        )
        // Right mask
        drawRect(
            color = dimmingColor,
            size = Size((w - squareSize) / 2, squareSize),
            topLeft = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2)
        )

        // Draw corners of the scanning frame
        val cornerLength = 80f
        val cornerWidth = 10f
        val cornerColor = Color.Yellow

        // Top-left corner
        drawLine(
            color = cornerColor,
            start = Offset(x = (w - squareSize) / 2, y = shiftTop),
            end = Offset(x = (w - squareSize) / 2 + cornerLength, y = shiftTop),
            strokeWidth = cornerWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = cornerColor,
            start = Offset(x = (w - squareSize) / 2, y = shiftTop),
            end = Offset(x = (w - squareSize) / 2, y = shiftTop + cornerLength),
            strokeWidth = cornerWidth,
            cap = StrokeCap.Round
        )

        // Top-right corner
        drawLine(
            color = cornerColor,
            start = Offset(x = w - (w - squareSize) / 2, y = shiftTop),
            end = Offset(x = w - (w - squareSize) / 2 - cornerLength, y = shiftTop),
            strokeWidth = cornerWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = cornerColor,
            start = Offset(x = w - (w - squareSize) / 2, y = shiftTop),
            end = Offset(x = w - (w - squareSize) / 2, y = shiftTop + cornerLength),
            strokeWidth = cornerWidth,
            cap = StrokeCap.Round
        )

        // Bottom-left corner
        drawLine(
            color = cornerColor,
            start = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2),
            end = Offset(x = (w - squareSize) / 2 + cornerLength, y = h - (h - squareSize) / 2),
            strokeWidth = cornerWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = cornerColor,
            start = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2),
            end = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - cornerLength),
            strokeWidth = cornerWidth,
            cap = StrokeCap.Round
        )

        // Bottom-right corner
        drawLine(
            color = cornerColor,
            start = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2),
            end = Offset(x = w - (w - squareSize) / 2 - cornerLength, y = h - (h - squareSize) / 2),
            strokeWidth = cornerWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = cornerColor,
            start = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2),
            end = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2 - cornerLength),
            strokeWidth = cornerWidth,
            cap = StrokeCap.Round
        )
    })
}
