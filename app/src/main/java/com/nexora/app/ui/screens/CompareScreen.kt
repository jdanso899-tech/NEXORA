package com.nexora.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareScreen(
    navController: NavController,
    assignmentId: String,
    friendPhone: String
) {
    // Placeholder texts – real app loads from SharedAnswer + local Assignment
    val myAnswer = remember { "My solution: … (loaded from assignment $assignmentId)" }
    val friendAnswer = remember { "Friend’s solution from $friendPhone: …" }
    var aiSummary by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compare Answers") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Your answer", fontWeight = FontWeight.SemiBold)
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(myAnswer, modifier = Modifier.padding(12.dp))
            }

            Text("Friend’s answer", fontWeight = FontWeight.SemiBold)
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(friendAnswer, modifier = Modifier.padding(12.dp))
            }

            Button(
                onClick = {
                    aiSummary = "AI comparison (demo):\n• Both answers cover the main points.\n• Your answer is more detailed on step 2.\n• Friend used a shorter method that is also valid.\nSuggestion: keep your structure and note the alternative approach."
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ask AI to compare")
            }

            aiSummary?.let {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(it, modifier = Modifier.padding(12.dp))
                }
            }
        }
    }
}
