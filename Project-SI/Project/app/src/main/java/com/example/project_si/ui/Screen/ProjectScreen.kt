package com.example.project_si.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
fun ProjectScreen(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Project", fontWeight = FontWeight.Bold, fontSize = 24.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF800080))
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .background(Color.Black)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cari Project Sistem Cerdas Yang Ingin Anda Ketahui",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar()
                Spacer(modifier = Modifier.height(16.dp))
                ProjectTable(snackbarHostState)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Nama Sistem Cerdas") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Button(
            onClick = { /* Logic untuk mencari project */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A2BE2))
        ) {
            Text("Cari", color = Color.White)
        }
    }
}

@Composable
fun ProjectTable(snackbarHostState: SnackbarHostState) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White) // Set background color to white for the table
        .padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray) // Header background color
                .padding(8.dp)
        ) {
            Text("Nama Sistem Cerdas", modifier = Modifier.weight(1f).padding(4.dp), fontWeight = FontWeight.Bold)
            Text("Format File Models", modifier = Modifier.weight(1f).padding(4.dp), fontWeight = FontWeight.Bold)
            Text("Actions", modifier = Modifier.weight(1f).padding(4.dp), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        ProjectRow(name = "aX", format = "Pickle", snackbarHostState)
        ProjectRow(name = "Ba", format = "CSV", snackbarHostState)
        ProjectRow(name = "5a", format = "Pickle", snackbarHostState)
    }
}

@Composable
fun ProjectRow(name: String, format: String, snackbarHostState: SnackbarHostState) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(name, modifier = Modifier.weight(1f).padding(4.dp))
        Text(format, modifier = Modifier.weight(1f).padding(4.dp))
        Button(
            onClick = {
                Log.d("ProjectRow", "Download button clicked for $name")
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Data Berhasil Didownload")
                }
            },
            modifier = Modifier.padding(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text("Download", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProjectScreenPreview() {
    ProjectSITheme {
        ProjectScreen(navController = rememberNavController())
    }
}
