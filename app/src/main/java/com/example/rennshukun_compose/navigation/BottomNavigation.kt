package com.example.rennshukun_compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigation(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Camera,
                    contentDescription = "barCodeScanner"
                )
            },
            label = { Text("BarCodeScanner") },
            selected = navController.currentDestination?.route == "barCodeScanner",
            onClick = { navController.navigate("barCodeScanner") }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = navController.currentDestination?.route == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            },
            label = { Text("Notifications") },
            selected = navController.currentDestination?.route == "notifications",
            onClick = { navController.navigate("notifications") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    val navController = rememberNavController()
    BottomNavigation(navController = navController)
}