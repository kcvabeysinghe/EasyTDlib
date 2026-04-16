package com.example.easytdlib.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.easytdlib.engine.TelegramManager
import org.drinkless.tdlib.TdApi

@Composable
fun ChatListItem(chat: TdApi.Chat, manager: TelegramManager) {
    // Observe the downloaded files map
    val downloadedFiles by manager.downloadedFiles.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- AVATAR LOGIC ---
        val photoFile = chat.photo?.small
        var imagePath: String? = null

        if (photoFile != null) {
            if (photoFile.local.isDownloadingCompleted) {
                // Image is already on device
                imagePath = photoFile.local.path
            } else {
                // Check if it just finished downloading in our state map
                imagePath = downloadedFiles[photoFile.id]
                // If not, tell the C++ engine to start downloading it right now
                if (imagePath == null) {
                    manager.downloadFile(photoFile.id)
                }
            }
        }

        if (imagePath != null) {
            // Render the downloaded local file using Coil
            AsyncImage(
                model = imagePath,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            // Fallback Placeholder while downloading or if no photo exists
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = chat.title.take(1).uppercase(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // --- TEXT LOGIC ---
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = chat.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
            // Placeholder text until we parse TdApi.MessageContent
            Text(
                text = if (chat.lastMessage != null) "Message received" else "No messages",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
    }
}