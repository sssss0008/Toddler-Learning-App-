package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ActivityType
import com.example.data.model.ModuleType
import com.example.ui.components.ChildAvatarView
import com.example.ui.components.SparkyMascot
import com.example.ui.components.StarBadge
import com.example.ui.theme.*
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.ToddlerViewModel

@Composable
fun HomeScreen(
    viewModel: ToddlerViewModel,
    modifier: Modifier = Modifier
) {
    val childProfile by viewModel.childProfile.collectAsState()
    val dailySteps by viewModel.dailyAdventure.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ToddlerBackground),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Top Header: Avatar, Name, Stars & Drawer Trigger
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        viewModel.speak("Hello ${childProfile.name}! You are doing amazing!")
                    }
                ) {
                    ChildAvatarView(avatarId = childProfile.avatarId, size = 52)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Hi, ${childProfile.name}! 👋",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = ToddlerTextPrimary
                        )
                        Text(
                            text = childProfile.journeyStage,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = ToddlerCoral
                        )
                    }
                }

                StarBadge(
                    stars = childProfile.totalStars,
                    modifier = Modifier.clickable {
                        viewModel.navigateTo(ScreenDestination.Achievements)
                    }
                )
            }
        }

        // Mascot Greeting
        item {
            SparkyMascot(
                message = "Let's Learn and Play! What shall we explore today?",
                onMascotClick = {
                    viewModel.speak("Let's Learn and Play! Tap any colorful card to start!")
                }
            )
        }

        // Hero Card: "Let's Learn and Play!"
        item {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .border(2.dp, ToddlerCoralLight, RoundedCornerShape(28.dp))
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2), Color(0xFFFFF8E1))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Let's Learn & Play! 🌟",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFE65100)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Discover letters, numbers, colors, animals, shapes, and more.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = ToddlerTextSecondary
                                )
                            }
                            Text(text = "🎈", fontSize = 42.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.navigateTo(ScreenDestination.LessonDetail(ModuleType.ABC, 0))
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = ToddlerCoral),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .shadow(4.dp, RoundedCornerShape(20.dp))
                                .testTag("continue_learning_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Start ABC Learning 🚀",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Today's Learning Adventure
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🗺️", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Today's Adventure",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = ToddlerTextPrimary
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFFF9C4)
                    ) {
                        Text(
                            text = "+3 Stars 🏆",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFF57F17),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(dailySteps) { step ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (step.isCompleted) Color(0xFFE8F5E9) else Color.White,
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .width(140.dp)
                                .border(
                                    2.dp,
                                    if (step.isCompleted) ToddlerGreen else ToddlerBorder,
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable {
                                    viewModel.navigateTo(ScreenDestination.LessonDetail(step.moduleType, 0))
                                }
                                .testTag("daily_step_${step.id}")
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(if (step.isCompleted) ToddlerGreenLight else ToddlerYellowLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = step.iconEmoji, fontSize = 26.sp)
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = step.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ToddlerTextPrimary,
                                    maxLines = 1
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (step.isCompleted) {
                                        Text(text = "Done ✓", color = ToddlerGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    } else {
                                        Text(text = "Play ➔", color = ToddlerCoral, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Quick Activities Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Fun Activities 🎮",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = ToddlerTextPrimary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Memory Games
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color(0xFFF3E5F5),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .weight(1f)
                            .height(115.dp)
                            .clickable {
                                viewModel.navigateTo(ScreenDestination.MemoryGame)
                            }
                            .testTag("home_memory_button")
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "🃏", fontSize = 32.sp)
                            Column {
                                Text(
                                    text = "Memory Game",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 15.sp,
                                    color = ToddlerPurple
                                )
                                Text(text = "Match pairs!", fontSize = 11.sp, color = ToddlerTextSecondary)
                            }
                        }
                    }

                    // Count Objects
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color(0xFFE1F5FE),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .weight(1f)
                            .height(115.dp)
                            .clickable {
                                viewModel.navigateTo(ScreenDestination.ActivityFlow(ActivityType.COUNT_OBJECTS))
                            }
                            .testTag("home_count_button")
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "🎈", fontSize = 32.sp)
                            Column {
                                Text(
                                    text = "Tap & Count",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 15.sp,
                                    color = ToddlerBlue
                                )
                                Text(text = "Pop balloons!", fontSize = 11.sp, color = ToddlerTextSecondary)
                            }
                        }
                    }
                }
            }
        }

        // Recommended Module Banner
        item {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .border(2.dp, ToddlerGreenLight, RoundedCornerShape(24.dp))
                    .clickable {
                        viewModel.navigateTo(ScreenDestination.LessonDetail(ModuleType.ANIMALS, 0))
                    }
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(ToddlerOrangeLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🦁", fontSize = 36.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Animal Safari 🌿",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ToddlerTextPrimary
                        )
                        Text(
                            text = "Meet friendly lions, farm animals, and hear their sounds!",
                            style = MaterialTheme.typography.bodySmall,
                            color = ToddlerTextSecondary
                        )
                    }

                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = ToddlerCoral,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}
