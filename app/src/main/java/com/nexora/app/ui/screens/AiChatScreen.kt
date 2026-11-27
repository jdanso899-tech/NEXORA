package com.nexora.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nexora.app.data.model.ChatMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiChatScreen(navController: NavController) {
    var messages by remember {
        mutableStateOf(
            listOf(
                ChatMessage(role = "assistant", content = "Hi! I'm NEXORA AI. Paste an assignment question or ask me to explain a concept. I can also help compare answers once you share with friends.")
            )
        )
    }
    var input by remember { mutableStateOf("") }
    var isThinking by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    fun sendMessage() {
        val text = input.trim()
        if (text.isBlank() || isThinking) return
        messages = messages + ChatMessage(role = "user", content = text)
        input = ""
        isThinking = true

        scope.launch {
            delay(900) // simulated network
            val reply = generateLocalReply(text)
            messages = messages + ChatMessage(role = "assistant", content = reply)
            isThinking = false
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("NEXORA AI", fontWeight = FontWeight.Bold)
                        Text("Assignment helper", style = MaterialTheme.typography.bodySmall)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(messages) { msg ->
                    val isUser = msg.role == "user"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .widthIn(max = 300.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (isUser) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .padding(12.dp)
                        ) {
                            Text(
                                msg.content,
                                color = if (isUser) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
                if (isThinking) {
                    item {
                        Text("Thinking…", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Ask about an assignment…") },
                    maxLines = 3
                )
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = { /* TODO: start SpeechRecognizer */ }) {
                    Icon(Icons.Default.Mic, contentDescription = "Voice")
                }
                IconButton(onClick = { sendMessage() }) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                }
            }
        }
    }
}

/**
 * Simple offline reply generator so the skeleton works without an API key.
 * Replace this with a real LLM call (Grok / Claude / GPT) later.
 */
private fun generateLocalReply(userText: String): String {
    val lower = userText.lowercase()
    return when {
        lower.contains("compare") -> "To compare answers: open Friends → choose a friend → Share your answer, then tap Compare. I can highlight differences once both sides are shared."
        lower.contains("due") || lower.contains("deadline") -> "You can set a due date when creating an assignment. NEXORA will speak the title and remaining time ~30 minutes before it is due."
        lower.contains("explain") || lower.contains("what is") -> "Sure — give me the concept or the exact question and I’ll break it down step by step."
        lower.contains("help") -> "I can: explain concepts, help structure an answer, generate practice questions, or compare your answer with a friend’s."
        else -> "Got it. Here’s a structured way to approach that:\n\n1. Restate the question in your own words.\n2. List the key facts or formulas.\n3. Show your working.\n4. Write a clear final answer.\n\nPaste the full question if you want a more specific walkthrough."
    }
}
