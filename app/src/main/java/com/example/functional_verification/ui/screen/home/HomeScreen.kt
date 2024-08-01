package com.example.functional_verification.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.functional_verification.ui.components.MessageList

@Preview(showBackground = true, widthDp = 415, heightDp = 923)
@Composable
fun HomeScreen() {
//    CenteredText("Home")
    MessageList(messages = listOf("A->", "B->", "C->", "D->", "E->", "F->", "G->"))
}
