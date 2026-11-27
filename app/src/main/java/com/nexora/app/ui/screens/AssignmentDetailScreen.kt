package com.nexora.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nexora.app.viewmodel.AssignmentViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentDetailScreen(navController: NavController, id: String) {
    val vm: AssignmentViewModel = viewModel()
    var assignment by remember { mutableStateOf(vm.getById(id)) }
    var answerText by remember { mutableStateOf(assignment?.answerText ?: "") }

    LaunchedEffect(id) {
        assignment = vm.getById(id)
        answerText = assignment?.answerText ?: ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(assignment?.title ?: "Assignment") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        assignment?.let {
                            vm.delete(it)
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            assignment?.let { a ->
                if (a.subject.isNotBlank()) {
                    Text(a.subject, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                }
                val dateFormat = SimpleDateFormat("EEE, MMM d yyyy • HH:mm", Locale.getDefault())
                Text("Due: ${dateFormat.format(Date(a.dueDateTime))}")

                if (a.description.isNotBlank()) {
                    Text(a.description, style = MaterialTheme.typography.bodyLarge)
                }

                HorizontalDivider()

                Text("Your Answer", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = answerText,
                    onValueChange = { answerText = it },
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    placeholder = { Text("Write your answer here…") }
                )

                Button(
                    onClick = {
                        val updated = a.copy(answerText = answerText, updatedAt = System.currentTimeMillis())
                        vm.update(updated)
                        assignment = updated
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Answer")
                }

                OutlinedButton(
                    onClick = { navController.navigate(com.nexora.app.ui.navigation.Routes.FRIENDS) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Share with Friends")
                }
            } ?: run {
                Text("Loading…")
            }
        }
    }
}
