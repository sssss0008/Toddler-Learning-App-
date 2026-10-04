package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun SparkyMascot(
    message: String = "Let's learn and play!",
    modifier: Modifier = Modifier,
    onMascotClick: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mascot_bounce")
    val bounceOffset by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Animated Mascot Character
        Box(
            modifier = Modifier
                .offset(y = bounceOffset.dp)
                .size(72.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0xFFFFEE58), Color(0xFFFFB300), Color(0xFFFF8F00))
                    )
                )
                .border(3.dp, Color.White, CircleShape)
                .clickable { onMascotClick() }
                .testTag("mascot_avatar"),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "⭐", fontSize = 32.sp)
                Text(
                    text = "Sparky",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF5D4037)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Speech Bubble
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 3.dp,
            modifier = Modifier
                .weight(1f)
                .border(2.dp, ToddlerYellowLight, RoundedCornerShape(20.dp))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = ToddlerTextPrimary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Speak",
                    tint = ToddlerCoral,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun AudioPlayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Tap to Listen",
    color: Color = ToddlerCoral
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
        modifier = modifier
            .minimumInteractiveComponentSize()
            .shadow(4.dp, RoundedCornerShape(24.dp))
            .testTag("audio_play_button")
    ) {
        Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun StarBadge(
    stars: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFFFF8E1),
        shadowElevation = 2.dp,
        modifier = modifier.border(2.dp, Color(0xFFFFD54F), RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "⭐", fontSize = 18.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "$stars",
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = Color(0xFFF57F17)
            )
        }
    }
}

@Composable
fun ChildAvatarView(
    avatarId: String,
    size: Int = 56,
    modifier: Modifier = Modifier
) {
    val (emoji, bg) = when (avatarId.lowercase()) {
        "bear" -> "🧸" to Color(0xFFFFCCBC)
        "bunny" -> "🐰" to Color(0xFFF8BBD0)
        "puppy" -> "🐶" to Color(0xFFD7CCC8)
        "kitty" -> "🐱" to Color(0xFFFFE082)
        "robot" -> "🤖" to Color(0xFFB3E5FC)
        "lion" -> "🦁" to Color(0xFFFFE0B2)
        else -> "⭐" to Color(0xFFFFF9C4)
    }

    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(bg)
            .border(2.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji, fontSize = (size * 0.55).sp)
    }
}

@Composable
fun ParentGateDialog(
    isOpen: Boolean,
    onSuccess: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    val num1 = remember { (3..7).random() }
    val num2 = remember { (2..6).random() }
    val expectedAnswer = num1 + num2
    var answerInput by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color.White,
            shadowElevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(ToddlerBlueLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Parent Gate",
                        tint = ToddlerBlue,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Grown-Ups Only",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = ToddlerTextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Please solve this quick problem to verify you are a parent:",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = ToddlerTextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF5F5F5),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "$num1  +  $num2  =  ?",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = ToddlerCoral,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = answerInput,
                    onValueChange = {
                        answerInput = it
                        isError = false
                    },
                    label = { Text("Your answer") },
                    singleLine = true,
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text("Oops, please try again", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("parent_gate_input")
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (answerInput.trim() == expectedAnswer.toString()) {
                                onSuccess()
                            } else {
                                isError = true
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("parent_gate_submit"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ToddlerBlue)
                    ) {
                        Text("Continue", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CelebrationOverlay(
    isVisible: Boolean,
    starsEarned: Int = 1,
    title: String = "Wonderful!",
    subtitle: String = "You earned a golden star!",
    onNext: () -> Unit,
    onReplay: () -> Unit
) {
    if (!isVisible) return

    val scaleAnim = remember { Animatable(0.4f) }
    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Dialog(onDismissRequest = onNext) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 16.dp,
            modifier = Modifier
                .scale(scaleAnim.value)
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFFFF9C4), Color.White)
                        )
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "🎉 ⭐ 🎉", fontSize = 42.sp)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Black,
                    color = ToddlerCoral,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = ToddlerTextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(starsEarned.coerceAtLeast(1)) {
                        Text(
                            text = "⭐",
                            fontSize = 44.sp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ToddlerGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("celebration_next_button")
                ) {
                    Text(
                        text = "Next Fun Activity! 🚀",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onReplay,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                ) {
                    Text(
                        text = "Play Again 🔄",
                        fontWeight = FontWeight.SemiBold,
                        color = ToddlerTextPrimary
                    )
                }
            }
        }
    }
}
