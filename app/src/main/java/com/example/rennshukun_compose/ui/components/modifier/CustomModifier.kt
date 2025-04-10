/**
 * File Name: CustomModifier.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2025/04/10
 * Copyright: © 2025 Gyoushin Ou. All rights reserved.
 * Description: Modifierカスタマイズ
 */

package com.example.rennshukun_compose.ui.components.modifier

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun Modifier.verticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp,
    barTint: Color = Color.Gray,
    backgroundColor: Color = Color.Gray.copy(alpha = 0.3f),
    isAlwaysVisible: Boolean = false,
    hideDelay: Long = 800,
    tag: String = "ScrollbarDebug",
): Modifier = composed {
    // スクロール可能かどうかをチェック
    val isScrollable by remember {
        derivedStateOf {
            // スクロール可能な条件：
            // 1. 全アイテム数が表示可能なアイテム数より多い、または
            // 2. すでにスクロールされている（firstVisibleItemIndex > 0）、または
            // 3. 最初のアイテムがすでに部分的にスクロールされている（firstVisibleItemScrollOffset > 0）
            val canScrollForward = state.canScrollForward
            val canScrollBackward = state.canScrollBackward
            canScrollForward || canScrollBackward
        }
    }

    var shouldBeVisible by remember { mutableStateOf(isAlwaysVisible) }

    // スクロール可能な場合のみアルファ値を計算
    val alpha by animateFloatAsState(
        targetValue = if (shouldBeVisible && isScrollable) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "scrollbarAlpha"
    )

    var targetScrollBarHeight by remember { mutableStateOf(0f) }
    var targetScrollPosition by remember { mutableStateOf(0f) }
    // デバッグ用
//	var lastScrollBarHeight by remember { mutableStateOf(0f) }
//	var lastScrollPosition by remember { mutableStateOf(0f) }

    val animatedScrollBarHeight by animateFloatAsState(
        targetValue = targetScrollBarHeight,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scrollbarHeight"
    )

    val animatedScrollPosition by animateFloatAsState(
        targetValue = targetScrollPosition,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scrollbarPosition"
    )

    val scrollKey = "${state.firstVisibleItemIndex}:${state.firstVisibleItemScrollOffset}"

    LaunchedEffect(isAlwaysVisible, scrollKey, isScrollable) {
        // スクロール可能な場合のみ表示状態を更新
        if (isAlwaysVisible && isScrollable) {
            shouldBeVisible = true
        } else if (isScrollable) {
            shouldBeVisible = true
            delay(hideDelay)
            shouldBeVisible = false
        } else {
            // スクロール不可の場合は常に非表示
            shouldBeVisible = false
        }
    }

    Modifier.drawWithContent {
        drawContent()

        // スクロール可能でない場合や、アルファ値が0の場合は描画しない
        if (alpha <= 0f || !isScrollable) return@drawWithContent

        val totalHeight = size.height
        val widthPx = width.toPx()

        val visibleItems = state.layoutInfo.visibleItemsInfo
        if (visibleItems.isEmpty()) return@drawWithContent

        val minBarHeight = 8f
        val maxBarHeight = totalHeight * 0.5f

        val totalItems = state.layoutInfo.totalItemsCount
        val fixedRatio = when {
            totalItems <= 10 -> 0.2f
            totalItems <= 30 -> 0.15f
            totalItems <= 100 -> 0.1f
            else -> 0.05f
        }

        val calculatedBarHeight = (totalHeight * fixedRatio).coerceIn(minBarHeight, maxBarHeight)

        val averageItemHeight = if (visibleItems.isNotEmpty()) {
            visibleItems.sumOf { it.size }.toFloat() / visibleItems.size
        } else {
            100f
        }

        val offsetBase = averageItemHeight * totalItems
        val currentScrollPixels =
            (state.firstVisibleItemIndex * averageItemHeight) + state.firstVisibleItemScrollOffset
        val totalScrollablePixels = offsetBase - totalHeight

        val scrollFraction = if (totalScrollablePixels <= 0) {
            0f
        } else {
            (currentScrollPixels / totalScrollablePixels).coerceIn(0f, 1f)
        }

        val calculatedPosition = (totalHeight - calculatedBarHeight) * scrollFraction

        targetScrollBarHeight = calculatedBarHeight
        targetScrollPosition = calculatedPosition

        // デバッグ用ログ出力
//		if(lastScrollBarHeight != animatedScrollBarHeight || lastScrollPosition != animatedScrollPosition) {
//			Log.d(
//				tag, "スクロールバー: height=${animatedScrollBarHeight}, pos=${animatedScrollPosition}, " +
//						"pixels=${currentScrollPixels}, fraction=${scrollFraction}, " +
//						"firstIdx=${state.firstVisibleItemIndex}, offset=${state.firstVisibleItemScrollOffset}, " +
//						"scrollable=${isScrollable}"
//			)
//			lastScrollBarHeight = animatedScrollBarHeight
//			lastScrollPosition = animatedScrollPosition
//		}

        drawRoundRect(
            color = backgroundColor,
            topLeft = Offset(x = size.width - widthPx, y = 0f),
            size = Size(widthPx, totalHeight),
            cornerRadius = CornerRadius(widthPx / 2, widthPx / 2),
            alpha = alpha * 0.8f
        )

        drawRoundRect(
            color = barTint,
            topLeft = Offset(x = size.width - widthPx, y = animatedScrollPosition),
            size = Size(widthPx, animatedScrollBarHeight),
            cornerRadius = CornerRadius(widthPx / 2, widthPx / 2),
            alpha = alpha
        )
    }
}

@Composable
fun Modifier.verticalScrollbarForScrollState(
    state: ScrollState,
    width: Dp = 8.dp,
    barTint: Color = Color.Gray,
    backgroundColor: Color = Color.Gray.copy(alpha = 0.3f),
    isAlwaysVisible: Boolean = false,
    hideDelay: Long = 800,
    tag: String = "ScrollbarDebug",
): Modifier = composed {
    // スクロール可能かどうかをチェック
    val isScrollable = remember {
        derivedStateOf {
            state.maxValue > 0
        }
    }

    var shouldBeVisible by remember { mutableStateOf(isAlwaysVisible) }

    val alpha by animateFloatAsState(
        targetValue = if (shouldBeVisible && isScrollable.value) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "scrollbarAlpha"
    )

    var targetScrollBarHeight by remember { mutableStateOf(0f) }
    var targetScrollPosition by remember { mutableStateOf(0f) }

    val animatedScrollBarHeight by animateFloatAsState(
        targetValue = targetScrollBarHeight,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scrollbarHeight"
    )

    val animatedScrollPosition by animateFloatAsState(
        targetValue = targetScrollPosition,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scrollbarPosition"
    )

    val scrollKey = state.value.toString() + state.isScrollInProgress.toString()

    LaunchedEffect(isAlwaysVisible, scrollKey, isScrollable.value) {
        if (isAlwaysVisible && isScrollable.value) {
            shouldBeVisible = true
        } else if (isScrollable.value && state.isScrollInProgress) {
            shouldBeVisible = true
            delay(hideDelay)
            shouldBeVisible = false
        } else if (!isScrollable.value) {
            shouldBeVisible = false
        }
    }

    Modifier.drawWithContent {
        drawContent()

        if (alpha <= 0f || !isScrollable.value) return@drawWithContent

        val totalHeight = size.height
        val widthPx = width.toPx()

        // **修正された計算ロジック**
        // スクロールバーの最小サイズを小さく設定
        val minBarHeight = 8f
        val maxBarHeight = totalHeight * 0.5f

        // スクロールバーの高さ計算を改善
        // ViewportHeight / ContentHeight の比率で計算
        val contentHeight = totalHeight + state.maxValue
        val viewportRatio = totalHeight / contentHeight.toFloat()

        // 固定の小さいサイズにする（項目数が多いため）
        // 表示項目数に関係なく常に適切なサイズを保つ
        val calculatedBarHeight = when {
            state.maxValue <= totalHeight * 2 -> totalHeight * 0.3f  // 少ない項目数の場合
            state.maxValue <= totalHeight * 5 -> totalHeight * 0.2f  // 中程度の項目数
            state.maxValue <= totalHeight * 10 -> totalHeight * 0.15f // 多めの項目数
            else -> totalHeight * 0.1f                              // 非常に多くの項目
        }.coerceIn(minBarHeight, maxBarHeight)

        // スクロール位置の計算
        val scrollFraction = if (state.maxValue == 0) {
            0f
        } else {
            (state.value.toFloat() / state.maxValue.toFloat()).coerceIn(0f, 1f)
        }

        val calculatedPosition = (totalHeight - calculatedBarHeight) * scrollFraction

        // デバッグ用ログ
        Log.d(
            tag,
            "ScrollState: value=${state.value}, maxValue=${state.maxValue}, " +
                    "viewportRatio=$viewportRatio, barHeight=$calculatedBarHeight, " +
                    "position=$calculatedPosition"
        )

        targetScrollBarHeight = calculatedBarHeight
        targetScrollPosition = calculatedPosition

        // スクロールバーの背景
        drawRoundRect(
            color = backgroundColor,
            topLeft = Offset(x = size.width - widthPx, y = 0f),
            size = Size(widthPx, totalHeight),
            cornerRadius = CornerRadius(widthPx / 2, widthPx / 2),
            alpha = alpha * 0.8f
        )

        // スクロールバー本体
        drawRoundRect(
            color = barTint,
            topLeft = Offset(x = size.width - widthPx, y = animatedScrollPosition),
            size = Size(widthPx, animatedScrollBarHeight),
            cornerRadius = CornerRadius(widthPx / 2, widthPx / 2),
            alpha = alpha
        )
    }
}