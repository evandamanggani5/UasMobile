package com.example.project_si.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sistem Implementasi", fontWeight = FontWeight.Bold, fontSize = 24.sp) },
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
                    .background(Color.Black)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selamat Datang Di Sistem Implementasi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color(0xFF8A2BE2)
                )
                Text(
                    text = "Sistem Implementasi Membantu Anda Dalam Memonitoring Perkembangan Project Sistem Cerdas Anda. Anda Dapat Menganalisa Dengan Membandingkan Project Sistem Cerdas Yang Ada Di Sini.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Cara Menggunakan Aplikasi Sistem Implementasi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color(0xFF8A2BE2)
                )
                Text(
                    text = "Project: Untuk melihat macam-macam Project yang telah dibuat atau di upload di sini.\nTransaction Data: Untuk membuat Project baru, serta menentukan anggota yang akan mengerjakan Project tersebut.\nEnvironment Data: Untuk melapor progress dari setiap anggota agar Project dapat dikembangkan secepat dan sebaik mungkin.\nPerformance Monitoring: Untuk melihat progress secara keseluruhan dari setiap anggota dalam mengembangkan Project.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tentang Sistem Implementasi?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color(0xFF8A2BE2)
                )
                Text(
                    text = "Sistem Implementasi sudah teruji dari segi desain UI, database, dan controller. Sistem Implementasi telah dianggap responsif dan efisien, sehingga User dapat mudah menggunakan aplikasi ini dalam memahami penggunaannya.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Kontak Kami",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8A2BE2),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text(
                            text = "081803492751\n08177062422\n082225653707\n081319776962\n081249409649",
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Social Media",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8A2BE2),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text(
                            text = "e_navarro_hk\nehzaifan\nrairdan\neverden\nrodrick",
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Kelompok 4",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8A2BE2),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text(
                            text = "Naufal Fawaz\nMohamad Fadilah Ariputra\nMuhamad Zidnan Fadilah\nEvanda Hangganji\nRodrick Kiddies",
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ProjectSITheme {
        HomeScreen(navController = rememberNavController())
    }
}
