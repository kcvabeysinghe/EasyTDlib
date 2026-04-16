package com.example.easytdlib.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.easytdlib.engine.TelegramManager
import com.example.easytdlib.ui.components.ChatListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(manager: TelegramManager) {
    val chatIds by manager.chatIds.collectAsState()
    val chats by manager.chats.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chats", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                actions = {
                    IconButton(onClick = { manager.logout() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (chatIds.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(chatIds) { id ->
                    val chat = chats[id]
                    if (chat != null) {
                        ChatListItem(chat = chat, manager = manager)
                    }
                }
            }
        }
    }
}