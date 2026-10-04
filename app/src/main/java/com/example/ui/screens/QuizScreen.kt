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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.SparkyMascot
import com.example.ui.theme.*
import com.example.viewmodel.ToddlerViewModel

data class QuizQuestionItem(
    val prompt: String,
    val speechText: String,
    val options: List<QuizChoice>
)

data class QuizChoice(
    val emoji: String,
    val label: String,
    val isCorrect: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: ToddlerViewModel,
    onBack: () -> Unit
) {
    val questions = remember {
        listOf(
            QuizQuestionItem(
                prompt = "Who says \"ROAR!\"? 🦁",
                speechText = "Who says roar?",
                options = listOf(
                    QuizChoice("🦁", "Lion", true),
                    QuizChoice("🐔", "Chicken", false),
                    QuizChoice("🐟", "Fish", false)
                )
            ),
            QuizQuestionItem(
                prompt = "Which one is sweet and RED? 🍓",
                speechText = "Which one is sweet and red?",
                options = listOf(
                    QuizChoice("🍓", "Strawberry", true),
                    QuizChoice("🍋", "Lemon", false),
                    QuizChoice("🐸", "Frog", false)
                )
            ),
            QuizQuestionItem(
                prompt = "Which vehicle flies in the sky? ✈️",
                speechText = "Which vehicle flies in the sky?",
                options = listOf(
                    QuizChoice("✈️", "Airplane", true),
                    QuizChoice("🚌", "Bus", false),
                    QuizChoice("⛵", "Boat", false)
                )
            ),
            QuizQuestionItem(
                prompt = "Find number THREE! 3️⃣",
                speechText = "Can you find number three?",
                options = listOf(
                    QuizChoice("3️⃣", "Three", true),
                    QuizChoice("1️⃣", "One", false),
                    QuizChoice("5️⃣", "Five", false)
                )
            )
        )
    }

    var questionIndex by remember { mutableIntStateOf(0) }
    val currentQuestion = questions[questionIndex]
    var showCelebration by remember { mutableStateOf(false) }

    LaunchedEffect(questionIndex) {
        viewModel.speak(currentQuestion.speechText)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Visual Quiz ⭐", fontWeight = FontWeight.Bold, color = ToddlerTextPrimary)
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("quiz_back")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = ToddlerYellowLight,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = "${questionIndex + 1} / ${questions.size}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color(0xFFF57F17),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ToddlerBackground)
            )
        },
        containerColor = ToddlerBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SparkyMascot(
                message = currentQuestion.prompt,
                onMascotClick = { viewModel.speak(currentQuestion.speechText) }
            )

            Surface(
                shape = RoundedCornerShape(28.dp),
                color = Color.White,
                shadowElevation = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .border(2.dp, ToddlerCoralLight, RoundedCornerShape(28.dp))
            ) {
                Text(
                    text = currentQuestion.prompt,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = ToddlerTextPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(24.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                currentQuestion.options.forEach { choice ->
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White,
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .border(2.dp, ToddlerYellowLight, RoundedCornerShape(24.dp))
                            .clickable {
                                if (choice.isCorrect) {
                                    viewModel.soundManager.playSuccessSound()
                                    viewModel.speak("Wonderful! You found ${choice.label}!")
                                    if (questionIndex < questions.size - 1) {
                                        questionIndex++
                                    } else {
                                        viewModel.onLessonCompleted(com.example.data.model.ModuleType.ABC, 2)
                                        showCelebration = true
                                    }
                                } else {
                                    viewModel.soundManager.playTryAgainSound()
                                    viewModel.speak("That's okay! Try one more time!")
                                }
                            }
                            .testTag("quiz_option_${choice.label}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = choice.emoji, fontSize = 42.sp)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = choice.label,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = ToddlerTextPrimary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        CelebrationOverlay(
            isVisible = showCelebration,
            title = "Quiz Master! 🌟",
            subtitle = "You answered all questions correctly!",
            starsEarned = 2,
            onNext = {
                showCelebration = false
                onBack()
            },
            onReplay = {
                showCelebration = false
                questionIndex = 0
            }
        )
    }
}
