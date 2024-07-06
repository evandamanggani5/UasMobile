package com.example.project_si

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project_si.repository.FirebaseRepository
import com.example.project_si.ui.screens.EnvironmentDataScreen
import com.example.project_si.ui.screens.HomeScreen
import com.example.project_si.ui.screens.PerformanceMonitoringScreen
import com.example.project_si.ui.screens.ProjectScreen
import com.example.project_si.ui.screens.TransactionDataScreen
import com.example.project_si.utils.theme.ProjectSITheme
import com.example.project_si.utils.ui.Screen
import com.example.project_si.viewmodel.DatasetViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FirebaseRepository
        val repository = FirebaseRepository()

        // Set up the content view with Compose
        setContent {
            val navController = rememberNavController()
            val viewModel: DatasetViewModel = viewModel()

            ProjectSITheme {
                NavHost(navController = navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) { HomeScreen(navController) }
                    composable(Screen.Project.route) { ProjectScreen(navController) }
                    composable(Screen.TransactionData.route) { TransactionDataScreen(navController, repository) }
                    composable(Screen.EnvironmentData.route) { EnvironmentDataScreen(navController) }
                    composable(Screen.PerformanceMonitoring.route) { PerformanceMonitoringScreen(navController) }
                }
            }
        }
    }
}
