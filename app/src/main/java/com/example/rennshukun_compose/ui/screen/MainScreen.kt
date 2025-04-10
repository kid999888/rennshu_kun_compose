package com.example.rennshukun_compose.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.rennshukun_compose.navigation.BottomNavigation
import com.example.rennshukun_compose.navigation.MainNavHost
import com.example.rennshukun_compose.ui.screen.bar_code_scanner.viewModel.BarCodeScannerViewModel
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true, widthDp = 415, heightDp = 923, showSystemUi = true)
@Composable
fun MainScreen() {
    val barCodeScannerViewModel: BarCodeScannerViewModel = koinViewModel()
    // remember変数や変数
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            barCodeScannerViewModel = barCodeScannerViewModel,
        )
    }
}
