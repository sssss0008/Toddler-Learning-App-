package com.example.ui.screens

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
import com.example.ui.components.ChildAvatarView
import com.example.ui.theme.*
import com.example.viewmodel.ToddlerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentSettingsScreen(
    viewModel: ToddlerViewModel,
    onBack: () -> Unit
) {
    val childProfile by viewModel.childProfile.collectAsState()
    val parentSettings by viewModel.parentSettings.collectAsState()

    var childName by remember { mutableStateOf(childProfile.name) }
    var selectedAge by remember { mutableStateOf(childProfile.ageRange) }
    var selectedAvatar by remember { mutableStateOf(childProfile.avatarId) }

    var narrationOn by remember { mutableStateOf(parentSettings.narrationEnabled) }
    var sfxOn by remember { mutableStateOf(parentSettings.soundEffectsEnabled) }
    var musicOn by remember { mutableStateOf(parentSettings.musicEnabled) }
    var sessionMinutes by remember { mutableIntStateOf(parentSettings.sessionTimerMinutes) }

    var showResetDialog by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Parent Settings ⚙️", fontWeight = FontWeight.Bold, color = ToddlerTextPrimary)
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("settings_back")) {
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
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Section 1: Child Profile
            item {
                Text(
                    text = "Child Profile 👶",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ToddlerTextPrimary,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = childName,
                            onValueChange = {
                                childName = it
                                viewModel.updateChildProfile(it, selectedAge, selectedAvatar)
                            },
                            label = { Text("Child Name / Nickname") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("settings_child_name_input")
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Target Age Range",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = ToddlerTextPrimary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("2–3", "3–4", "4–5").forEach { age ->
                                val isSelected = selectedAge == age
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        selectedAge = age
                                        viewModel.updateChildProfile(childName, age, selectedAvatar)
                                    },
                                    label = { Text("$age Years") }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Select Avatar",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = ToddlerTextPrimary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(listOf("star", "bear", "bunny", "puppy", "kitty", "robot", "lion")) { av ->
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .border(
                                            if (selectedAvatar == av) 3.dp else 1.dp,
                                            if (selectedAvatar == av) ToddlerCoral else Color.LightGray,
                                            CircleShape
                                        )
                                        .clickable {
                                            selectedAvatar = av
                                            viewModel.updateChildProfile(childName, selectedAge, av)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    ChildAvatarView(avatarId = av, size = 48)
                                }
                            }
                        }
                    }
                }
            }

            // Section 2: Audio & Narration
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Audio & Speech Settings 🔊",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ToddlerTextPrimary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Voice Guidance & Narration", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                Text("Speaks letter names and gentle prompts", fontSize = 12.sp, color = ToddlerTextSecondary)
                            }
                            Switch(
                                checked = narrationOn,
                                onCheckedChange = {
                                    narrationOn = it
                                    viewModel.updateParentSettings(it, sfxOn, musicOn, sessionMinutes, selectedAge)
                                }
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFF0F0F0))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Sound Effects & Chimes", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                Text("Joyful star sounds and pops", fontSize = 12.sp, color = ToddlerTextSecondary)
                            }
                            Switch(
                                checked = sfxOn,
                                onCheckedChange = {
                                    sfxOn = it
                                    viewModel.updateParentSettings(narrationOn, it, musicOn, sessionMinutes, selectedAge)
                                }
                            )
                        }
                    }
                }
            }

            // Section 3: Parent-Controlled Freemium & Unlock
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Subscription & Unlocks (Parent Only) 💎",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = ToddlerTextPrimary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFFFF8E1),
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, ToddlerYellow, RoundedCornerShape(20.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (parentSettings.isSubscribed) "Current Plan: ${parentSettings.subscriptionPlan} ⭐" else "Free Explorer Plan Active",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Color(0xFFE65100)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Unlock all 8 full curricula, full 26-letter phonics library, all 20 animals, unlimited memory levels, and complete offline access.",
                            fontSize = 12.sp,
                            color = ToddlerTextSecondary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    viewModel.upgradeSubscription("Monthly Unlimited ($4.99)")
                                    snackbarMessage = "Monthly plan unlocked successfully!"
                                },
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = ToddlerBlue),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Monthly\n$4.99", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    viewModel.upgradeSubscription("Yearly Explorer ($29.99)")
                                    snackbarMessage = "Yearly plan unlocked successfully!"
                                },
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = ToddlerGreen),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Yearly\n$29.99", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    viewModel.upgradeSubscription("Lifetime Family ($49.99)")
                                    snackbarMessage = "Lifetime plan unlocked successfully!"
                                },
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = ToddlerPurple),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Lifetime\n$49.99", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Section 4: Data Management & Reset
            item {
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedButton(
                    onClick = { showResetDialog = true },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reset Child Learning Progress 🔄", fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Reset confirmation dialog
        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = { Text("Reset Progress?") },
                text = { Text("This will reset all completed lessons, earned stars, and achievements back to zero.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.resetAllLearningData()
                            showResetDialog = false
                            snackbarMessage = "All progress has been reset."
                        }
                    ) {
                        Text("Reset", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
