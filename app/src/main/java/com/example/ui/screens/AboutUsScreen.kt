package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.ToddlerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    viewModel: ToddlerViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("About Us ℹ️", fontWeight = FontWeight.Bold, color = ToddlerTextPrimary)
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("about_back")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ToddlerBackground)
            )
        },
        containerColor = ToddlerBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(ToddlerCoralLight),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🌟", fontSize = 44.sp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Toddler Learning Hub",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black,
                    color = ToddlerCoral
                )

                Text(
                    text = "Version 1.0 • Educational Edition",
                    style = MaterialTheme.typography.bodySmall,
                    color = ToddlerTextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Product Vision",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ToddlerTextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Toddler Learning Hub is a premium Android educational app designed specifically for children ages 2–5 years. It transforms early learning into a playful interactive adventure featuring alphabet recognition, counting, colors, shapes, animals, vehicles, and memory development.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = ToddlerTextSecondary,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // Developer Card with Awiskar Acharya & LinkedIn
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, ToddlerBlueLight, RoundedCornerShape(22.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "👨‍💻 Developed By",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ToddlerTextSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Awiskar Acharya",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black,
                            color = ToddlerBlue
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                viewModel.requestParentAccess {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/awiskaracharya/"))
                                    context.startActivity(intent)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ToddlerBlue),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("linkedin_button")
                        ) {
                            Text(text = "Connect on LinkedIn", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.Default.OpenInNew, contentDescription = "LinkedIn", modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }

            // Child Safety & Privacy Policy
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Child Safety & Privacy Promise 🛡️",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• No child-targeted advertising\n• No third-party data tracking\n• Offline-first local storage\n• Parent Gate on all external links and purchases\n• Distraction-free early preschool experience",
                            style = MaterialTheme.typography.bodySmall,
                            color = ToddlerTextSecondary,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}
