/**
 * File Name: MainNavHost.kt
 * Project Name: Rennshu Kun
 * Creator: Gyoushin Ou
 * Creation Date: 2025/04/10
 * Copyright: © 2025 Gyoushin Ou. All rights reserved.
 * Description: Mainナビホスト
 */

package com.example.rennshukun_compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rennshukun_compose.ui.screen.bar_code_scanner.BarCodeScannerScreen
import com.example.rennshukun_compose.ui.screen.bar_code_scanner.view_model.BarCodeScannerViewModel
import com.example.rennshukun_compose.ui.screen.home.HomeScreen
import com.example.rennshukun_compose.ui.screen.notifications.NotificationsDetailScreen
import com.example.rennshukun_compose.ui.screen.notifications.NotificationsScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    barCodeScannerViewModel: BarCodeScannerViewModel,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "home",
    ) {
        composable(route = "barCodeScanner") {
            BarCodeScannerScreen(
                modifier = modifier,
                navController = navController,
                viewModel = barCodeScannerViewModel,
            )
        }
        composable(route = "home") {
            HomeScreen()
        }
        composable(route = "notifications") {
            NotificationsScreen(
                navController,
            )
        }
        composable("notifications_detail") {
            NotificationsDetailScreen { message ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("message", message)
                navController.popBackStack()
            }
        }
    }
}
