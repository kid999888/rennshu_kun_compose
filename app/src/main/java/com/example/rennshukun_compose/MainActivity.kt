package com.example.rennshukun_compose

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.rennshukun_compose.data.room.dao.MessageListDao
import com.example.rennshukun_compose.data.room.entity.MessageEntity
import com.example.rennshukun_compose.ui.screen.MainScreen
import com.example.rennshukun_compose.ui.theme.FunctionalVerificationDefaultTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val messageListDao: MessageListDao by inject()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // 权限获取成功
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestCameraPermission()
        setContent {
            FunctionalVerificationDefaultTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }

        lifecycleScope.launch {
            // データベースバッチ処理
            if (messageListDao.getAll().isEmpty()) {
                insertNotificationData(messageListDao)
            }
        }

    }

    private suspend fun insertNotificationData(
        messageListDao: MessageListDao,
    ) {
        val mockNotifications = listOf(
            MessageEntity(message = "a"),
            MessageEntity(message = "aa"),
            MessageEntity(message = "aaa"),
            MessageEntity(message = "aaaa"),
            MessageEntity(message = "aaaaa"),
            MessageEntity(message = "aaaaaa"),
        )

        messageListDao.insert(mockNotifications)
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // 已经有权限
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}
