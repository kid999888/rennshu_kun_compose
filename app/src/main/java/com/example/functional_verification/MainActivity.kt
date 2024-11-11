package com.example.functional_verification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.functional_verification.data.room.dao.MessageListDao
import com.example.functional_verification.data.room.entity.MessageEntity
import com.example.functional_verification.ui.screen.MainScreen
import com.example.functional_verification.ui.theme.FunctionalVerificationDefaultTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val messageListDao: MessageListDao by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}
