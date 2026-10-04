package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.ToddlerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(
    viewModel: ToddlerViewModel,
    onBack: () -> Unit
) {
    val achievements by viewModel.allAchievements.collectAsState()
    val childProfile by viewModel.childProfile.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Badges & Stars 🏆", fontWeight = FontWeight.Bold, color = ToddlerTextPrimary)
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("achievements_back")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFFFF9C4),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = "${childProfile.totalStars} ⭐",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = Color(0xFFF57F17),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
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
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🌟", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Learning Journey: ${childProfile.journeyStage}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = ToddlerCoral
                        )
                        Text(
                            text = "Keep playing and learning to unlock more shiny badges!",
                            style = MaterialTheme.typography.bodySmall,
                            color = ToddlerTextSecondary
                        )
                    }
                }
            }

            items(achievements) { item ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (item.isUnlocked) Color.White else Color(0xFFF5F5F5),
                    shadowElevation = if (item.isUnlocked) 2.dp else 0.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.5.dp,
                            if (item.isUnlocked) ToddlerYellow else Color(0xFFE0E0E0),
                            RoundedCornerShape(20.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(if (item.isUnlocked) Color(0xFFFFF9C4) else Color(0xFFE0E0E0)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (item.isUnlocked) item.iconEmoji else "🔒",
                                fontSize = 28.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = if (item.isUnlocked) ToddlerTextPrimary else Color.Gray
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = item.description,
                                fontSize = 12.sp,
                                color = if (item.isUnlocked) ToddlerTextSecondary else Color.LightGray
                            )
                        }

                        if (item.isUnlocked) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = ToddlerGreenLight
                            ) {
                                Text(
                                    text = "Unlocked ✓",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ToddlerGreen,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
