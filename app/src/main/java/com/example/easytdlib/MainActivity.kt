package com.example.easytdlib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import org.drinkless.tdlib.TdApi
import com.example.easytdlib.engine.TelegramManager
import com.example.easytdlib.ui.screens.ChatListScreen

class MainActivity : ComponentActivity() {

    private lateinit var telegramManager: TelegramManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        telegramManager = TelegramManager(applicationContext)
        telegramManager.initClient()

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val authState by telegramManager.authState.collectAsState()

                    Crossfade(targetState = authState, label = "Auth Transition") { state ->
                        AuthRouter(state, telegramManager)
                    }
                }
            }
        }
    }
}

@Composable
fun AuthRouter(authState: TdApi.AuthorizationState?, manager: TelegramManager) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (authState?.constructor) {
            TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR -> {
                var phoneNumber by remember { mutableStateOf("") }

                Text("Sign in to Telegram", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number (+CountryCode)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { manager.sendPhoneNumber(phoneNumber) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = phoneNumber.isNotBlank()
                ) {
                    Text("Send Code")
                }
            }

            TdApi.AuthorizationStateWaitCode.CONSTRUCTOR -> {
                var code by remember { mutableStateOf("") }

                Text("Enter Code", style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = "We've sent a verification code to your Telegram app.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Verification Code") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { manager.sendVerificationCode(code) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = code.isNotBlank()
                ) {
                    Text("Verify & Login")
                }
            }

            TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR -> {
                var password by remember { mutableStateOf("") }

                Text("Two-Step Verification", style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = "Your account is protected with an additional password.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Enter 2FA Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { manager.sendPassword(password) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = password.isNotBlank()
                ) {
                    Text("Submit Password")
                }
            }

            TdApi.AuthorizationStateReady.CONSTRUCTOR -> {
                // Pointing directly to the Chat List
                ChatListScreen(manager)
            }

            else -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Booting TDLib Engine...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}