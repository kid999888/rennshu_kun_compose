/**
 * File Name: NotificationsDetailScreen.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2024/11/22
 * Copyright: © 2024 Gyoushin Ou. All rights reserved.
 * Description:
 */

package com.example.functional_verification.ui.screen.notifications

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun NotificationsDetailScreen(
    onBackClick: (String) -> Unit,
) {
    // BackHandler方法
    BackHandler {
        onBackClick("Message from detail screen")
    }
}