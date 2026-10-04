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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ActivityType
import com.example.data.model.ModuleType
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.SparkyMascot
import com.example.ui.theme.*
import com.example.viewmodel.ToddlerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    activityType: ActivityType,
    viewModel: ToddlerViewModel,
    onBack: () -> Unit
) {
    var showCelebration by remember { mutableStateOf(false) }
    var celebrationTitle by remember { mutableStateOf("Awesome!") }
    var celebrationSubtitle by remember { mutableStateOf("You did it!") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = activityType.title,
                        fontWeight = FontWeight.Bold,
                        color = ToddlerTextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("activity_back")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ToddlerBackground)
            )
        },
        containerColor = ToddlerBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (activityType) {
                ActivityType.LETTER_TAP -> LetterTapActivity(
                    viewModel = viewModel,
                    onSuccess = {
                        celebrationTitle = "Letter Master! 🔤"
                        celebrationSubtitle = "You tapped the right letter!"
                        showCelebration = true
                    }
                )
                ActivityType.COUNT_OBJECTS -> CountBalloonsActivity(
                    viewModel = viewModel,
                    onSuccess = {
                        celebrationTitle = "Counting Champion! 🎈"
                        celebrationSubtitle = "You counted all the balloons!"
                        showCelebration = true
                    }
                )
                ActivityType.COLOR_TAP -> ColorTapActivity(
                    viewModel = viewModel,
                    onSuccess = {
                        celebrationTitle = "Color Star! 🎨"
                        celebrationSubtitle = "You found the right color!"
                        showCelebration = true
                    }
                )
                ActivityType.SHAPE_MATCH -> ShapeMatchActivity(
                    viewModel = viewModel,
                    onSuccess = {
                        celebrationTitle = "Shape Genius! 🔷"
                        celebrationSubtitle = "You matched the shape perfectly!"
                        showCelebration = true
                    }
                )
                else -> LetterTapActivity(
                    viewModel = viewModel,
                    onSuccess = {
                        showCelebration = true
                    }
                )
            }

            CelebrationOverlay(
                isVisible = showCelebration,
                title = celebrationTitle,
                subtitle = celebrationSubtitle,
                onNext = {
                    showCelebration = false
                    onBack()
                },
                onReplay = {
                    showCelebration = false
                }
            )
        }
    }
}

@Composable
private fun LetterTapActivity(
    viewModel: ToddlerViewModel,
    onSuccess: () -> Unit
) {
    val targetLetter = remember { "B" }
    val options = remember { listOf("A", "B", "C").shuffled() }
    var hintText by remember { mutableStateOf("Tap the letter '$targetLetter'") }

    LaunchedEffect(Unit) {
        viewModel.speak("Can you tap the letter B?")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        SparkyMascot(
            message = hintText,
            onMascotClick = { viewModel.speak("Can you tap the letter B?") }
        )

        Text(
            text = "Find the letter:",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = ToddlerTextPrimary
        )

        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFFFFF3E0),
            shadowElevation = 2.dp,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = targetLetter,
                fontSize = 80.sp,
                fontWeight = FontWeight.Black,
                color = ToddlerCoral,
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEach { letter ->
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .size(90.dp)
                        .border(3.dp, if (letter == targetLetter) ToddlerBlue else ToddlerYellowLight, RoundedCornerShape(24.dp))
                        .clickable {
                            if (letter == targetLetter) {
                                viewModel.soundManager.playSuccessSound()
                                viewModel.speak("Great job! B is for Butterfly!")
                                onSuccess()
                            } else {
                                viewModel.soundManager.playTryAgainSound()
                                hintText = "That's okay! Try letter $targetLetter!"
                                viewModel.speak("That's okay! Let's try again! Find letter B!")
                            }
                        }
                        .testTag("tap_option_$letter")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = letter,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Black,
                            color = ToddlerTextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CountBalloonsActivity(
    viewModel: ToddlerViewModel,
    onSuccess: () -> Unit
) {
    var count by remember { mutableIntStateOf(0) }
    val totalBalloons = 5
    val balloonColors = listOf(Color(0xFFEF5350), Color(0xFF42A5F5), Color(0xFFFFCA28), Color(0xFF66BB6A), Color(0xFFAB47BC))
    val popped = remember { mutableStateListOf(false, false, false, false, false) }

    LaunchedEffect(Unit) {
        viewModel.speak("Let's count balloons! Tap each balloon to pop it!")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SparkyMascot(
            message = if (count == 0) "Tap the balloons to count!" else "You counted $count balloons!",
            onMascotClick = { viewModel.speak("Tap each balloon to count them!") }
        )

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🎈 Count: ", style = MaterialTheme.typography.headlineMedium, color = ToddlerTextPrimary)
                Text(
                    text = "$count / $totalBalloons",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Black,
                    color = ToddlerCoral
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 0 until totalBalloons) {
                val isPopped = popped[i]
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(if (isPopped) Color(0xFFEEEEEE) else balloonColors[i])
                        .clickable(enabled = !isPopped) {
                            popped[i] = true
                            count++
                            viewModel.soundManager.playTapPop()
                            viewModel.speak("$count")
                            if (count >= totalBalloons) {
                                viewModel.onLessonCompleted(ModuleType.NUMBERS, 1)
                                onSuccess()
                            }
                        }
                        .testTag("balloon_$i"),
                    contentAlignment = Alignment.Center
                ) {
                    if (isPopped) {
                        Text(text = "✨", fontSize = 24.sp)
                    } else {
                        Text(text = "🎈", fontSize = 34.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ColorTapActivity(
    viewModel: ToddlerViewModel,
    onSuccess: () -> Unit
) {
    val targetColor = "Blue"
    val choices = remember {
        listOf(
            Triple("Red", "🍓", Color(0xFFEF5350)),
            Triple("Blue", "🌊", Color(0xFF1E88E5)),
            Triple("Yellow", "☀️", Color(0xFFFDD835))
        ).shuffled()
    }

    LaunchedEffect(Unit) {
        viewModel.speak("Can you find the blue ocean?")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        SparkyMascot(
            message = "Find and tap the BLUE color! 🌊",
            onMascotClick = { viewModel.speak("Can you find the blue color?") }
        )

        Text(
            text = "Tap the Blue one! 🎨",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = Color(0xFF1E88E5)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            choices.forEach { (name, emoji, color) ->
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .size(96.dp)
                        .border(3.dp, color, RoundedCornerShape(24.dp))
                        .clickable {
                            if (name == targetColor) {
                                viewModel.soundManager.playSuccessSound()
                                viewModel.speak("Wonderful! That's Blue!")
                                onSuccess()
                            } else {
                                viewModel.soundManager.playTryAgainSound()
                                viewModel.speak("That's $name. Try finding Blue!")
                            }
                        }
                        .testTag("color_choice_$name")
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = emoji, fontSize = 40.sp)
                        Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = color)
                    }
                }
            }
        }
    }
}

@Composable
private fun ShapeMatchActivity(
    viewModel: ToddlerViewModel,
    onSuccess: () -> Unit
) {
    val choices = remember {
        listOf(
            Triple("Round Ball", "⚽", true),
            Triple("Window", "🪟", false),
            Triple("Pizza Slice", "🍕", false)
        ).shuffled()
    }

    LaunchedEffect(Unit) {
        viewModel.speak("Which object is round like a circle?")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        SparkyMascot(
            message = "Which object is shaped like a CIRCLE? ⭕",
            onMascotClick = { viewModel.speak("Which object is shaped like a circle?") }
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFEBEE))
                .border(4.dp, ToddlerCoral, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "⭕", fontSize = 54.sp)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            choices.forEach { (name, emoji, isCorrect) ->
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .width(96.dp)
                        .height(110.dp)
                        .border(2.dp, ToddlerYellowLight, RoundedCornerShape(24.dp))
                        .clickable {
                            if (isCorrect) {
                                viewModel.soundManager.playSuccessSound()
                                viewModel.speak("Yes! The ball is round like a circle!")
                                onSuccess()
                            } else {
                                viewModel.soundManager.playTryAgainSound()
                                viewModel.speak("That's okay! Try the round bouncy ball!")
                            }
                        }
                        .testTag("shape_choice_$name")
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = emoji, fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = name,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ToddlerTextPrimary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
