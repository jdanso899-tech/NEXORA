package com.nexora.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nexora.app.ui.screens.*

object Routes {
    const val HOME = "home"
    const val ASSIGNMENTS = "assignments"
    const val ASSIGNMENT_DETAIL = "assignment/{id}"
    const val ADD_ASSIGNMENT = "add_assignment"
    const val FRIENDS = "friends"
    const val AI_CHAT = "ai_chat"
    const val PROFILE = "profile"
    const val COMPARE = "compare/{assignmentId}/{friendPhone}"
}

@Composable
fun NexoraNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(navController)
        }
        composable(Routes.ASSIGNMENTS) {
            AssignmentsScreen(navController)
        }
        composable(Routes.ADD_ASSIGNMENT) {
            AddEditAssignmentScreen(navController)
        }
        composable("assignment/{id}") { backStack ->
            val id = backStack.arguments?.getString("id") ?: return@composable
            AssignmentDetailScreen(navController, id)
        }
        composable(Routes.FRIENDS) {
            FriendsScreen(navController)
        }
        composable(Routes.AI_CHAT) {
            AiChatScreen(navController)
        }
        composable(Routes.PROFILE) {
            ProfileScreen(navController)
        }
        composable("compare/{assignmentId}/{friendPhone}") { backStack ->
            val assignmentId = backStack.arguments?.getString("assignmentId") ?: return@composable
            val friendPhone = backStack.arguments?.getString("friendPhone") ?: return@composable
            CompareScreen(navController, assignmentId, friendPhone)
        }
    }
}
