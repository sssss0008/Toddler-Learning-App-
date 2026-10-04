package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CurriculumData
import com.example.data.model.ModuleType
import com.example.ui.components.AudioPlayButton
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.StarBadge
import com.example.ui.theme.*
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.ToddlerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailLessonScreen(
    moduleType: ModuleType,
    initialIndex: Int = 0,
    viewModel: ToddlerViewModel,
    onBack: () -> Unit
) {
    val lessons = remember(moduleType) { CurriculumData.getLessonsForModule(moduleType) }
    var currentIndex by remember { mutableIntStateOf(initialIndex.coerceIn(0, (lessons.size - 1).coerceAtLeast(0))) }
    val currentLesson = lessons.getOrNull(currentIndex) ?: return
    var showCelebration by remember { mutableStateOf(false) }

    // Speak upon lesson change
    LaunchedEffect(currentLesson.id) {
        viewModel.speak(currentLesson.speechText)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = moduleType.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = ToddlerTextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("lesson_back_button")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = ToddlerTextPrimary
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = moduleType.lightColor,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = "${currentIndex + 1} / ${lessons.size}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = moduleType.themeColor,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ToddlerBackground)
            )
        },
        bottomBar = {
            // Toddler Bottom Control Bar: Big Previous, Big Next, Star Button
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous
                    IconButton(
                        onClick = {
                            if (currentIndex > 0) currentIndex--
                        },
                        enabled = currentIndex > 0,
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(if (currentIndex > 0) Color(0xFFF5F5F5) else Color(0xFFFAFAFA))
                            .testTag("lesson_prev_button")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous",
                            tint = if (currentIndex > 0) ToddlerTextPrimary else Color.LightGray,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // I Learned This! Button
                    Button(
                        onClick = {
                            viewModel.onLessonCompleted(moduleType, 1)
                            showCelebration = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ToddlerYellow),
                        shape = RoundedCornerShape(24.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
                        modifier = Modifier
                            .height(52.dp)
                            .shadow(2.dp, RoundedCornerShape(24.dp))
                            .testTag("lesson_star_button")
                    ) {
                        Text(text = "⭐", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "I Learned This!",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Color(0xFF5D4037)
                        )
                    }

                    // Next
                    IconButton(
                        onClick = {
                            if (currentIndex < lessons.size - 1) {
                                currentIndex++
                            } else {
                                showCelebration = true
                            }
                        },
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(moduleType.themeColor)
                            .shadow(3.dp, CircleShape)
                            .testTag("lesson_next_button")
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        },
        containerColor = ToddlerBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Main Big Learning Card
            Surface(
                shape = RoundedCornerShape(32.dp),
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(3.dp, moduleType.lightColor, RoundedCornerShape(32.dp))
                    .clickable {
                        viewModel.speak(currentLesson.speechText)
                    }
                    .testTag("lesson_main_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(moduleType.lightColor.copy(alpha = 0.4f), Color.White)
                            )
                        )
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    // Category / Sound Prompt Badge
                    if (currentLesson.soundPrompt.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFFFF8E1),
                            modifier = Modifier.border(1.dp, ToddlerYellow, RoundedCornerShape(16.dp))
                        ) {
                            Text(
                                text = "🔊 \"${currentLesson.soundPrompt}\"",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFE65100),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    } else if (currentLesson.pronunciation.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = moduleType.lightColor
                        ) {
                            Text(
                                text = "Sound: ${currentLesson.pronunciation}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = moduleType.themeColor,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }

                    // Huge Focus Display (Letter, Number, or Shape)
                    Text(
                        text = currentLesson.title,
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Black,
                        color = currentLesson.accentColor,
                        textAlign = TextAlign.Center
                    )

                    // Big Emoji Visual
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .shadow(2.dp, CircleShape)
                            .border(3.dp, moduleType.lightColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentLesson.visualEmoji,
                            fontSize = 72.sp
                        )
                    }

                    // Subtitle / Concept Name
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = currentLesson.subtitle,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Black,
                            color = ToddlerTextPrimary,
                            textAlign = TextAlign.Center
                        )

                        if (currentLesson.realWorldExample.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentLesson.realWorldExample,
                                style = MaterialTheme.typography.bodyMedium,
                                color = ToddlerTextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Audio Listen Button
                    AudioPlayButton(
                        onClick = {
                            viewModel.speak(currentLesson.speechText)
                        },
                        label = "Listen & Repeat 🔊",
                        color = moduleType.themeColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Celebration Dialog when completed
        CelebrationOverlay(
            isVisible = showCelebration,
            title = "Wonderful Job! 🎉",
            subtitle = "You learned ${currentLesson.title} (${currentLesson.subtitle})!",
            onNext = {
                showCelebration = false
                if (currentIndex < lessons.size - 1) {
                    currentIndex++
                } else {
                    onBack()
                }
            },
            onReplay = {
                showCelebration = false
                viewModel.speak(currentLesson.speechText)
            }
        )
    }
}
