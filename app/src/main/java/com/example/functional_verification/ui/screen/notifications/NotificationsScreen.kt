package com.example.functional_verification.ui.screen.notifications

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.functional_verification.ui.components.CenteredText

@Composable
fun NotificationsScreen(
    navController: NavController,
) {

    val context = LocalContext.current
    // NavController方法
    var message by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.get<String>("message")?.let {
                message = it
            }
    }
    LaunchedEffect(message) {
        message?.let {
            Log.d("NotificationsScreen", "Received message: $it")
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                navController.navigate("notifications_detail")
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Go to Detail")
        }

        message?.let {
            CenteredText(it)
        }
    }
}