package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CelebrationOverlay
import com.example.ui.components.SparkyMascot
import com.example.ui.theme.*
import com.example.viewmodel.ToddlerViewModel

data class MemoryCard(
    val id: Int,
    val emoji: String,
    val name: String,
    var isFlipped: Boolean = false,
    var isMatched: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryGameScreen(
    viewModel: ToddlerViewModel,
    onBack: () -> Unit
) {
    val itemsPool = listOf("🦁" to "Lion", "🍎" to "Apple", "🐶" to "Dog", "🚗" to "Car", "⭐" to "Star", "🍓" to "Berry")
    var pairCount by remember { mutableIntStateOf(3) } // 3 pairs = 6 cards for toddler

    var cards by remember(pairCount) {
        mutableStateOf(
            itemsPool.take(pairCount).flatMapIndexed { index, (emoji, name) ->
                listOf(
                    MemoryCard(id = index * 2, emoji = emoji, name = name),
                    MemoryCard(id = index * 2 + 1, emoji = emoji, name = name)
                )
            }.shuffled()
        )
    }

    var selectedFirst by remember { mutableStateOf<Int?>(null) }
    var selectedSecond by remember { mutableStateOf<Int?>(null) }
    var isBusy by remember { mutableStateOf(false) }
    var showWinDialog by remember { mutableStateOf(false) }

    fun resetGame() {
        cards = itemsPool.take(pairCount).flatMapIndexed { index, (emoji, name) ->
            listOf(
                MemoryCard(id = index * 2, emoji = emoji, name = name),
                MemoryCard(id = index * 2 + 1, emoji = emoji, name = name)
            )
        }.shuffled()
        selectedFirst = null
        selectedSecond = null
        isBusy = false
        showWinDialog = false
    }

    LaunchedEffect(Unit) {
        viewModel.speak("Memory Game! Find matching pairs!")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Memory Pairs 🃏", fontWeight = FontWeight.Bold, color = ToddlerTextPrimary)
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("memory_back")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { resetGame() }, modifier = Modifier.testTag("memory_refresh")) {
                        Icon(Icons.Default.Refresh, contentDescription = "Restart", tint = ToddlerCoral)
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
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SparkyMascot(
                message = "Tap two cards to find a match!",
                onMascotClick = { viewModel.speak("Tap two cards to find a match!") }
            )

            // Difficulty selector pills (2 pairs, 3 pairs, 4 pairs)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                listOf(2 to "2 Pairs", 3 to "3 Pairs", 4 to "4 Pairs").forEach { (pairs, label) ->
                    val isSelected = pairCount == pairs
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) ToddlerPurple else Color.White,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .border(1.dp, ToddlerPurpleLight, RoundedCornerShape(16.dp))
                            .clickable {
                                pairCount = pairs
                            }
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else ToddlerTextPrimary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(if (pairCount == 2) 2 else 3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                itemsIndexed(cards) { index, card ->
                    val isVisible = card.isFlipped || card.isMatched || index == selectedFirst || index == selectedSecond

                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = if (isVisible) Color.White else ToddlerPurpleLight,
                        shadowElevation = 3.dp,
                        modifier = Modifier
                            .height(115.dp)
                            .border(
                                2.dp,
                                if (card.isMatched) ToddlerGreen else if (isVisible) ToddlerPurple else ToddlerBorder,
                                RoundedCornerShape(22.dp)
                            )
                            .clickable(enabled = !isBusy && !card.isMatched && index != selectedFirst) {
                                viewModel.soundManager.playTapPop()
                                if (selectedFirst == null) {
                                    selectedFirst = index
                                } else if (selectedSecond == null) {
                                    selectedSecond = index
                                    isBusy = true

                                    val firstIdx = selectedFirst!!
                                    val secondIdx = index

                                    if (cards[firstIdx].emoji == cards[secondIdx].emoji) {
                                        // Match found!
                                        cards = cards.mapIndexed { i, c ->
                                            if (i == firstIdx || i == secondIdx) c.copy(isMatched = true) else c
                                        }
                                        viewModel.soundManager.playSuccessSound()
                                        viewModel.speak("${cards[firstIdx].name}! You found a pair!")
                                        selectedFirst = null
                                        selectedSecond = null
                                        isBusy = false

                                        // Check if all matched
                                        if (cards.all { it.isMatched }) {
                                            viewModel.onMemoryGameWon()
                                            showWinDialog = true
                                        }
                                    } else {
                                        // Not matched, flip back after short delay
                                        viewModel.soundManager.playTryAgainSound()
                                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                            selectedFirst = null
                                            selectedSecond = null
                                            isBusy = false
                                        }, 900)
                                    }
                                }
                            }
                            .testTag("memory_card_$index")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (isVisible) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = card.emoji, fontSize = 44.sp)
                                    Text(text = card.name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ToddlerTextPrimary)
                                }
                            } else {
                                Text(text = "❓", fontSize = 34.sp)
                            }
                        }
                    }
                }
            }
        }

        CelebrationOverlay(
            isVisible = showWinDialog,
            title = "Memory Champion! 🏆",
            subtitle = "You matched all the pairs!",
            starsEarned = 2,
            onNext = {
                showWinDialog = false
                onBack()
            },
            onReplay = {
                resetGame()
            }
        )
    }
}
