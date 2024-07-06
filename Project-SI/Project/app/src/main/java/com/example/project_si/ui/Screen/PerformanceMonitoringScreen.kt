package com.example.project_si.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.project_si.utils.theme.ProjectSITheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerformanceMonitoringScreen(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lihat Hasil Deploy Models Nya Disini", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF800080))
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Update Status Project Anda Disini",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8A2BE2)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Deploy Telah Selesai",
                                actionLabel = "Close",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800080))
                ) {
                    Text("Update Status", color = Color.White, fontSize = 20.sp)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PerformanceMonitoringScreenPreview() {
    ProjectSITheme {
        PerformanceMonitoringScreen(navController = rememberNavController())
    }
}
