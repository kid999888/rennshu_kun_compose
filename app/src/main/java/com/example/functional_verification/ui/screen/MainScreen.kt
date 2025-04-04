package com.example.functional_verification.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.functional_verification.ui.navigation.BottomNavigation
import com.example.functional_verification.ui.screen.barCodeScanner.BarCodeScannerScreen
import com.example.functional_verification.ui.screen.home.HomeScreen
import com.example.functional_verification.ui.screen.notifications.NotificationsDetailScreen
import com.example.functional_verification.ui.screen.notifications.NotificationsScreen

@Preview(showBackground = true, widthDp = 415, heightDp = 923, showSystemUi = true)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = "barCodeScanner") {
                BarCodeScannerScreen()
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
}
