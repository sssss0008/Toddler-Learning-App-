package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.data.model.CurriculumData
import com.example.data.model.ModuleType
import com.example.ui.components.ChildAvatarView
import com.example.ui.theme.*
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.ToddlerViewModel

@Composable
fun ParentDashboardScreen(
    viewModel: ToddlerViewModel,
    modifier: Modifier = Modifier
) {
    val childProfile by viewModel.childProfile.collectAsState()
    val progressList by viewModel.allProgress.collectAsState()
    val parentSettings by viewModel.parentSettings.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ToddlerBackground),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
        // Parent Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Parent Area 🛡️",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Black,
                            color = ToddlerTextPrimary
                        )
                        Text(
                            text = "Insights & settings for grown-ups",
                            style = MaterialTheme.typography.bodyMedium,
                            color = ToddlerTextSecondary
                        )
                    }

                    IconButton(
                        onClick = { viewModel.navigateTo(ScreenDestination.ParentSettings) },
                        modifier = Modifier.testTag("parent_settings_icon")
                    ) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = ToddlerCoral,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        // Child Profile Summary Card
        item {
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = Color.White,
                shadowElevation = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .border(2.dp, ToddlerBlueLight, RoundedCornerShape(26.dp))
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ChildAvatarView(avatarId = childProfile.avatarId, size = 64)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = childProfile.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = ToddlerTextPrimary
                        )
                        Text(
                            text = "Age Group: ${childProfile.ageRange} Years • ${childProfile.journeyStage}",
                            style = MaterialTheme.typography.bodySmall,
                            color = ToddlerTextSecondary
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFFF9C4),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "⭐", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${childProfile.totalStars}",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF57F17)
                            )
                        }
                    }
                }
            }
        }

        // Weekly Summary Metrics
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Text(
                    text = "Weekly Activity Overview 📊",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ToddlerTextPrimary,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricBox(
                        title = "Practice Time",
                        value = "38m",
                        sub = "Gentle pace",
                        color = ToddlerBlue,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "Lessons Done",
                        value = "${progressList.sumOf { it.completedLessons }}",
                        sub = "Great progress",
                        color = ToddlerGreen,
                        modifier = Modifier.weight(1f)
                    )
                    MetricBox(
                        title = "Correct Taps",
                        value = "${progressList.sumOf { it.correctAnswers }}",
                        sub = "High accuracy",
                        color = ToddlerCoral,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Descriptive Recommendations
        item {
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color(0xFFF1F8E9),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .border(1.5.dp, Color(0xFFC5E1A5), RoundedCornerShape(22.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "💡", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Learning Recommendation",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF33691E)
                        )
                        Text(
                            text = "Your child has explored colors and ABCs frequently! Try exploring Shape Matching and Counting balloons next for balanced discovery.",
                            fontSize = 12.sp,
                            color = ToddlerTextSecondary
                        )
                    }
                }
            }
        }

        // Curriculum Progress Breakdown
        item {
            Text(
                text = "Module Progress Breakdown 📈",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = ToddlerTextPrimary,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }

        items(ModuleType.entries) { module ->
            val progress = progressList.find { it.moduleId == module.name }
            val total = CurriculumData.getLessonsForModule(module).size
            val completed = progress?.completedLessons ?: 0
            val ratio = if (total > 0) completed.toFloat() / total else 0f

            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = module.emoji, fontSize = 26.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = module.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = ToddlerTextPrimary
                            )
                            Text(
                                text = "$completed / $total (${(ratio * 100).toInt()}%)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = module.themeColor
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { ratio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = module.themeColor,
                            trackColor = Color(0xFFEEEEEE)
                        )
                    }
                }
            }
        }

        // Fast Action Buttons for Parent
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { viewModel.navigateTo(ScreenDestination.Achievements) },
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ToddlerPurple),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text(text = "🏆 Achievements", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Button(
                    onClick = { viewModel.navigateTo(ScreenDestination.AboutUs) },
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ToddlerCoral),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text(text = "ℹ️ About & Dev", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun MetricBox(
    title: String,
    value: String,
    sub: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 11.sp, color = ToddlerTextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Black, color = color)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = sub, fontSize = 10.sp, color = Color.Gray)
        }
    }
}
