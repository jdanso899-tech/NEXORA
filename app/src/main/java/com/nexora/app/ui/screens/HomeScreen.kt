package com.nexora.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nexora.app.data.model.Assignment
import com.nexora.app.ui.navigation.Routes
import com.nexora.app.viewmodel.AssignmentViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val vm: AssignmentViewModel = viewModel()
    val upcoming by vm.upcoming.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NEXORA", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.AI_CHAT) }) {
                        Icon(Icons.Default.SmartToy, contentDescription = "AI Chat")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.ADD_ASSIGNMENT) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Assignment")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Upcoming",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            if (upcoming.isEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier = Modifier.padding(20.dp)) {
                            Text("No upcoming assignments")
                            Text(
                                "Tap + to add your first task",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(upcoming) { assignment ->
                    AssignmentCard(assignment) {
                        navController.navigate("assignment/${assignment.id}")
                    }
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                Text(
                    "Quick actions",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AssistChip(
                        onClick = { navController.navigate(Routes.AI_CHAT) },
                        label = { Text("Ask AI") }
                    )
                    AssistChip(
                        onClick = { navController.navigate(Routes.FRIENDS) },
                        label = { Text("Friends") }
                    )
                    AssistChip(
                        onClick = { navController.navigate(Routes.ASSIGNMENTS) },
                        label = { Text("All tasks") }
                    )
                }
            }
        }
    }
}

@Composable
fun AssignmentCard(assignment: Assignment, onClick: () -> Unit) {
    val dateFormat = SimpleDateFormat("EEE, MMM d • HH:mm", Locale.getDefault())
    val dueText = dateFormat.format(Date(assignment.dueDateTime))
    val minutesLeft = ((assignment.dueDateTime - System.currentTimeMillis()) / 60000).coerceAtLeast(0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(assignment.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            if (assignment.subject.isNotBlank()) {
                Text(assignment.subject, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.height(6.dp))
            Text(dueText, style = MaterialTheme.typography.bodyMedium)
            if (minutesLeft < 120) {
                Text(
                    "Due in $minutesLeft min",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
