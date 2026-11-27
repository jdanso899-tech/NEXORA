package com.nexora.app.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nexora.app.data.model.Friend

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(navController: NavController) {
    val context = LocalContext.current
    // Tries real contacts first; falls back to demo data if permission not granted or empty
    var friends by remember {
        mutableStateOf(
            run {
                val real = try {
                    com.nexora.app.util.ContactsHelper.loadContacts(context)
                } catch (e: Exception) {
                    emptyList()
                }
                if (real.isNotEmpty()) real
                else listOf(
                    Friend(phoneNumber = "+15551234567", displayName = "Alex Rivera", isNexoraUser = true),
                    Friend(phoneNumber = "+15559876543", displayName = "Jordan Lee", isNexoraUser = true),
                    Friend(phoneNumber = "+15555551212", displayName = "Sam Okonkwo", isNexoraUser = false)
                )
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Friends", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* refresh contacts */ }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    "Friends are matched from contacts who also use NEXORA.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(8.dp))
            }

            items(friends) { friend ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(friend.displayName, fontWeight = FontWeight.SemiBold)
                            Text(
                                if (friend.isNexoraUser) "On NEXORA" else "Invite",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (friend.isNexoraUser) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${friend.phoneNumber}"))
                            context.startActivity(intent)
                        }) {
                            Icon(Icons.Default.Call, contentDescription = "Call")
                        }
                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${friend.phoneNumber}"))
                            context.startActivity(intent)
                        }) {
                            Icon(Icons.Default.Message, contentDescription = "Text")
                        }
                    }
                }
            }
        }
    }
}
