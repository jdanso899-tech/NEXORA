package com.nexora.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nexora.app.data.model.Assignment
import com.nexora.app.util.ReminderScheduler
import com.nexora.app.viewmodel.AssignmentViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditAssignmentScreen(navController: NavController) {
    val vm: AssignmentViewModel = viewModel()
    val context = androidx.compose.ui.platform.LocalContext.current

    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var hoursFromNow by remember { mutableStateOf("24") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Assignment") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description / Notes") },
                modifier = Modifier.fillMaxWidth().height(120.dp)
            )
            OutlinedTextField(
                value = hoursFromNow,
                onValueChange = { hoursFromNow = it.filter { c -> c.isDigit() } },
                label = { Text("Due in how many hours?") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (title.isBlank()) return@Button
                    val hours = hoursFromNow.toLongOrNull() ?: 24L
                    val due = Calendar.getInstance().apply {
                        add(Calendar.HOUR_OF_DAY, hours.toInt())
                    }.timeInMillis

                    val assignment = Assignment(
                        title = title.trim(),
                        subject = subject.trim(),
                        description = description.trim(),
                        dueDateTime = due
                    )
                    vm.save(assignment)
                    // Schedule spoken reminder 30 min before
                    ReminderScheduler.schedule(context, assignment, leadMinutes = 30)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Assignment")
            }
        }
    }
}
