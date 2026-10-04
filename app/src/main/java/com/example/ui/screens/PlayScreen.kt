package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PlayArrow
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
import com.example.ui.components.SparkyMascot
import com.example.ui.theme.*
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.ToddlerViewModel

@Composable
fun PlayScreen(
    viewModel: ToddlerViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ToddlerBackground),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Play & Practice! 🎮",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Black,
                    color = ToddlerTextPrimary
                )
                Text(
                    text = "Playful mini-games to practice what you learned!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = ToddlerTextSecondary
                )
            }
        }

        // Mascot prompt
        item {
            SparkyMascot(
                message = "Playing games makes learning super fun! Choose an activity!",
                onMascotClick = {
                    viewModel.speak("Playing games makes learning super fun! Choose an activity!")
                }
            )
        }

        // Quick Play Banner
        item {
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .border(2.dp, ToddlerYellowLight, RoundedCornerShape(26.dp))
                    .clickable {
                        viewModel.speak("Quick Play! Let's pop some balloons!")
                        viewModel.navigateTo(ScreenDestination.ActivityFlow(ActivityType.COUNT_OBJECTS))
                    }
                    .testTag("quick_play_hero")
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFFE1F5FE), Color(0xFFB3E5FC))
                            )
                        )
                        .padding(18.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = ToddlerBlue,
                                modifier = Modifier.padding(bottom = 6.dp)
                            ) {
                                Text(
                                    text = "30-60 SECONDS",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Text(
                                text = "Quick Play Mode ⚡",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF01579B)
                            )
                            Text(
                                text = "Bite-sized playful challenges for quick learning fun!",
                                style = MaterialTheme.typography.bodySmall,
                                color = ToddlerTextSecondary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .shadow(2.dp, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = ToddlerBlue,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
        }

        // Section: Games List
        item {
            Text(
                text = "Interactive Games 🧩",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = ToddlerTextPrimary,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }

        // Game 1: Memory Cards
        item {
            PlayActivityCard(
                title = "Memory Pairs",
                subtitle = "Flip and match cute animals, fruits & shapes",
                emoji = "🃏",
                bgGradient = listOf(Color(0xFFF3E5F5), Color(0xFFE1BEE7)),
                accentColor = ToddlerPurple,
                onClick = {
                    viewModel.navigateTo(ScreenDestination.MemoryGame)
                },
                testTag = "play_memory"
            )
        }

        // Game 2: Letter Tap
        item {
            PlayActivityCard(
                title = "Letter Tap",
                subtitle = "Listen to the letter sound and tap the match",
                emoji = "🔤",
                bgGradient = listOf(Color(0xFFFFEBEE), Color(0xFFFFCDD2)),
                accentColor = ColorAbc,
                onClick = {
                    viewModel.navigateTo(ScreenDestination.ActivityFlow(ActivityType.LETTER_TAP))
                },
                testTag = "play_letter_tap"
            )
        }

        // Game 3: Tap & Count
        item {
            PlayActivityCard(
                title = "Count the Balloons",
                subtitle = "Tap floating balloons to count 1, 2, 3 together!",
                emoji = "🎈",
                bgGradient = listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9)),
                accentColor = ToddlerGreen,
                onClick = {
                    viewModel.navigateTo(ScreenDestination.ActivityFlow(ActivityType.COUNT_OBJECTS))
                },
                testTag = "play_count"
            )
        }

        // Game 4: Color Tap
        item {
            PlayActivityCard(
                title = "Color Tap",
                subtitle = "Find and tap the matching colors",
                emoji = "🎨",
                bgGradient = listOf(Color(0xFFFFF8E1), Color(0xFFFFECB3)),
                accentColor = ToddlerYellow,
                onClick = {
                    viewModel.navigateTo(ScreenDestination.ActivityFlow(ActivityType.COLOR_TAP))
                },
                testTag = "play_color_tap"
            )
        }

        // Game 5: Shape Matching
        item {
            PlayActivityCard(
                title = "Shape Matching",
                subtitle = "Match shapes to real-world objects like balls and windows",
                emoji = "🔷",
                bgGradient = listOf(Color(0xFFE0F7FA), Color(0xFFB2EBF2)),
                accentColor = ColorVehicles,
                onClick = {
                    viewModel.navigateTo(ScreenDestination.ActivityFlow(ActivityType.SHAPE_MATCH))
                },
                testTag = "play_shape_match"
            )
        }

        // Game 6: Fun Quiz
        item {
            PlayActivityCard(
                title = "Visual Quiz Star",
                subtitle = "Gentle voice questions with big picture answers",
                emoji = "⭐",
                bgGradient = listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2)),
                accentColor = ToddlerOrange,
                onClick = {
                    viewModel.navigateTo(ScreenDestination.Quiz)
                },
                testTag = "play_quiz"
            )
        }
    }
}

@Composable
private fun PlayActivityCard(
    title: String,
    subtitle: String,
    emoji: String,
    bgGradient: List<Color>,
    accentColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .background(Brush.horizontalGradient(bgGradient))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .shadow(1.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 30.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ToddlerTextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = ToddlerTextSecondary
                )
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = accentColor,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
