package com.example.project_si.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.project_si.utils.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnvironmentDataScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lihat Hasil Deploy Models Nya Disini", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF800080))
            )
        },
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
                    text = "Pickle File Data",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8A2BE2)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Accuracy: 1.0",
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Test Results",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8A2BE2)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TestResultsTable()

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate(Screen.PerformanceMonitoring.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800080))
                ) {
                    Text("Next", color = Color.White)
                }
            }
        }
    )
}

@Composable
fun TestResultsTable() {
    val testData = listOf(
        Triple(0, 1, 1),
        Triple(1, 0, 0),
        Triple(2, 2, 2),
        Triple(3, 1, 1),
        Triple(4, 1, 1),
        Triple(5, 0, 0),
        Triple(6, 1, 1),
        Triple(7, 2, 2),
        Triple(8, 1, 1),
        Triple(9, 1, 1),
        Triple(10, 2, 2),
        Triple(11, 0, 0),
        Triple(12, 0, 0),
        Triple(13, 0, 0),
        Triple(14, 0, 0),
        Triple(15, 1, 1)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp)
        ) {
            Text("Index", modifier = Modifier.weight(1f).padding(8.dp), color = Color.White)
            Text("True Label", modifier = Modifier.weight(1f).padding(8.dp), color = Color.White)
            Text("Predicted Label", modifier = Modifier.weight(1f).padding(8.dp), color = Color.White)
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(testData) { (index, trueLabel, predictedLabel) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(8.dp)
                ) {
                    Text("$index", modifier = Modifier.weight(1f).padding(8.dp), color = Color.Black)
                    Text("$trueLabel", modifier = Modifier.weight(1f).padding(8.dp), color = Color.Black)
                    Text("$predictedLabel", modifier = Modifier.weight(1f).padding(8.dp), color = Color.Black)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnvironmentDataScreenPreview() {
    ProjectSITheme {
        EnvironmentDataScreen(navController = rememberNavController())
    }
}
