package com.example.functional_verification.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.functional_verification.ui.screen.home.viewModel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true, widthDp = 415, heightDp = 923)
@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    //val viewModel: HomeViewModel = viewModel()
    //    CenteredText("Home")
    MessageList(messages = listOf("A->", "B->", "C->", "D->", "E->", "F->", "G->"))
}

@Composable
fun MessageList(messages: List<String>) {
    Column {
        var selectAll = false
        Checkbox(
            checked = false,
            onCheckedChange = { checkedState ->
                selectAll = checkedState
            },
        )
        if (messages.isEmpty()) {
            Text("No messages")
        } else {
            messages.forEach { message ->
                Text(text = message)
            }
        }

    }
}
