package com.example.project_si.ui.screens

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.project_si.model.Dataset
import com.example.project_si.repository.FirebaseRepository
import com.example.project_si.utils.theme.ProjectSITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDataScreen(navController: NavHostController, repository: FirebaseRepository) {
    var pickleFiles by remember { mutableStateOf(listOf("Pickle File 1", "Pickle File 2")) }
    var csvFiles by remember { mutableStateOf(listOf("CSV File 1", "CSV File 2")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upload File Models Project", fontWeight = FontWeight.Bold, fontSize = 24.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF800080))
            )
        },
        content = { paddingValues ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Upload File Models Project Yang Anda Download Disini Untuk Deploy",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color(0xFF8A2BE2)
                )

                UploadSection(navController, repository) { fileType, fileName ->
                    when (fileType) {
                        "Pickle" -> pickleFiles = pickleFiles + fileName
                        "CSV" -> csvFiles = csvFiles + fileName
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                FileListSection(
                    title = "List of Pickle Files",
                    files = pickleFiles
                )

                Spacer(modifier = Modifier.height(16.dp))

                FileListSection(
                    title = "List of CSV Files",
                    files = csvFiles
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@Composable
fun UploadSection(navController: NavHostController, repository: FirebaseRepository, onFileUploaded: (String, String) -> Unit) {
    var projectName by remember { mutableStateOf("Unnamed Project") }
    var selectedFileName by remember { mutableStateOf("No file chosen") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileType by remember { mutableStateOf("Pickle") }
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileName = it.lastPathSegment ?: "No file chosen"
            selectedFileUri = it
            Log.d("UploadSection", "File selected: $selectedFileName")
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            selectedImageBitmap = bitmap
            imageUrl = it.toString()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        selectedImageBitmap = bitmap
        // No need to save to file, just handle the bitmap directly
    }

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            // Handle the case where the user denied the permission
            // You can show a message to the user here
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FileUploadForm(
            projectName = projectName,
            onProjectNameChange = { projectName = it },
            selectedFileName = selectedFileName,
            fileType = "Pickle",
            onPickFileClick = {
                selectedFileType = "Pickle"
                filePickerLauncher.launch("application/octet-stream")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        FileUploadForm(
            projectName = projectName,
            onProjectNameChange = { projectName = it },
            selectedFileName = selectedFileName,
            fileType = "CSV",
            onPickFileClick = {
                selectedFileType = "CSV"
                filePickerLauncher.launch("text/csv")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons for image picking and taking photo
        Row {
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Select Photo")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                val permissionCheck = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                if (permissionCheck == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(null)
                } else {
                    requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                }
            }) {
                Text("Take Photo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                selectedFileUri?.let {
                    val dataset = Dataset(
                        nama_project = projectName,
                        fileUrl = ""
                    )
                    repository.createDatasetWithFile(
                        dataset,
                        it,
                        onSuccess = {
                            Log.d("UploadSection", "File uploaded and dataset created")
                            onFileUploaded(selectedFileType, selectedFileName)
                        },
                        onFailure = { exception ->
                            Log.e("UploadSection", "Error: ${exception.message}")
                        }
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800080))
        ) {
            Text("Upload", color = Color.White)
        }

        imageUrl = imageUrl  // Add this line for the image URL
    }
}

@Composable
fun FileUploadForm(
    projectName: String,
    onProjectNameChange: (String) -> Unit,
    selectedFileName: String,
    fileType: String,
    onPickFileClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
        TextField(
            value = projectName,
            onValueChange = onProjectNameChange,
            label = { Text("Project name:") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        TextField(
            value = selectedFileName,
            onValueChange = {},
            label = { Text("File:") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Button(onClick = onPickFileClick, modifier = Modifier.padding(8.dp)) {
            Text("Pick $fileType File")
        }
    }
}

@Composable
fun FileListSection(title: String, files: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8A2BE2)
        )
        Spacer(modifier = Modifier.height(8.dp))
        files.forEach { file ->
            FileRow(file)
        }
    }
}

@Composable
fun FileRow(fileName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Project Name", modifier = Modifier.weight(1f).padding(4.dp))
        Text(fileName, modifier = Modifier.weight(1f).padding(4.dp))
        Text("Action", modifier = Modifier.weight(1f).padding(4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDataScreenPreview() {
    ProjectSITheme {
        TransactionDataScreen(navController = rememberNavController(), repository = FirebaseRepository())
    }
}
