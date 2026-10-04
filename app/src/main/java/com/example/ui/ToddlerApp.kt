package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.data.model.ActivityType
import com.example.data.model.ModuleType
import com.example.ui.components.ParentGateDialog
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.ScreenDestination
import com.example.viewmodel.ToddlerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToddlerApp(
    viewModel: ToddlerViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentScreen by viewModel.currentScreen.collectAsState()
    val currentTab by viewModel.currentBottomTab.collectAsState()
    val isParentGateOpen by viewModel.parentGateOpen.collectAsState()

    // Handle back button on sub-screens
    BackHandler(enabled = currentScreen !is ScreenDestination.Home) {
        when (currentScreen) {
            is ScreenDestination.LessonDetail,
            is ScreenDestination.ActivityFlow,
            ScreenDestination.MemoryGame,
            ScreenDestination.Quiz,
            ScreenDestination.Achievements,
            ScreenDestination.ParentSettings,
            ScreenDestination.AboutUs -> {
                viewModel.navigateTo(ScreenDestination.Home)
            }
            ScreenDestination.Learn, ScreenDestination.Play, ScreenDestination.Parent -> {
                viewModel.selectBottomTab(0)
            }
            else -> {}
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight(),
                drawerContainerColor = Color.White
            ) {
                DrawerContent(
                    onItemClick = { screen ->
                        scope.launch { drawerState.close() }
                        if (screen is ScreenDestination.Parent || screen is ScreenDestination.ParentSettings) {
                            viewModel.requestParentAccess {
                                viewModel.navigateTo(screen)
                            }
                        } else {
                            viewModel.navigateTo(screen)
                        }
                    },
                    onCloseDrawer = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                // Show standard TopBar on primary 4 tabs
                if (currentScreen is ScreenDestination.Home ||
                    currentScreen is ScreenDestination.Learn ||
                    currentScreen is ScreenDestination.Play ||
                    currentScreen is ScreenDestination.Parent
                ) {
                    CenterAlignedTopAppBar(
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "⭐ ", fontSize = 20.sp)
                                Text(
                                    text = "Toddler Hub",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 20.sp,
                                    color = ToddlerCoral
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } },
                                modifier = Modifier.testTag("app_drawer_button")
                            ) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = ToddlerTextPrimary,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    val isMuted = !viewModel.soundManager.isMuted
                                    viewModel.soundManager.isMuted = isMuted
                                    if (!isMuted) viewModel.speak("Sound on!")
                                },
                                modifier = Modifier.testTag("mute_toggle_button")
                            ) {
                                Icon(
                                    imageVector = if (viewModel.soundManager.isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                                    contentDescription = "Sound Toggle",
                                    tint = ToddlerCoral
                                )
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = ToddlerBackground
                        )
                    )
                }
            },
            bottomBar = {
                // Exactly 4 Bottom Navigation Items
                if (currentScreen is ScreenDestination.Home ||
                    currentScreen is ScreenDestination.Learn ||
                    currentScreen is ScreenDestination.Play ||
                    currentScreen is ScreenDestination.Parent
                ) {
                    NavigationBar(
                        containerColor = Color.White,
                        tonalElevation = 8.dp,
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .testTag("bottom_nav_bar")
                    ) {
                        NavigationBarItem(
                            selected = currentTab == 0,
                            onClick = { viewModel.selectBottomTab(0) },
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(26.dp)) },
                            label = { Text("Home", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ToddlerCoral,
                                selectedTextColor = ToddlerCoral,
                                indicatorColor = ToddlerCoralLight
                            ),
                            modifier = Modifier.testTag("bottom_tab_home")
                        )

                        NavigationBarItem(
                            selected = currentTab == 1,
                            onClick = { viewModel.selectBottomTab(1) },
                            icon = { Icon(Icons.Default.School, contentDescription = "Learn", modifier = Modifier.size(26.dp)) },
                            label = { Text("Learn", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ToddlerBlue,
                                selectedTextColor = ToddlerBlue,
                                indicatorColor = ToddlerBlueLight
                            ),
                            modifier = Modifier.testTag("bottom_tab_learn")
                        )

                        NavigationBarItem(
                            selected = currentTab == 2,
                            onClick = { viewModel.selectBottomTab(2) },
                            icon = { Icon(Icons.Default.Games, contentDescription = "Play", modifier = Modifier.size(26.dp)) },
                            label = { Text("Play", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ToddlerGreen,
                                selectedTextColor = ToddlerGreen,
                                indicatorColor = ToddlerGreenLight
                            ),
                            modifier = Modifier.testTag("bottom_tab_play")
                        )

                        NavigationBarItem(
                            selected = currentTab == 3,
                            onClick = { viewModel.selectBottomTab(3) },
                            icon = { Icon(Icons.Default.Lock, contentDescription = "Parent", modifier = Modifier.size(26.dp)) },
                            label = { Text("Parent", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ToddlerPurple,
                                selectedTextColor = ToddlerPurple,
                                indicatorColor = ToddlerPurpleLight
                            ),
                            modifier = Modifier.testTag("bottom_tab_parent")
                        )
                    }
                }
            },
            containerColor = ToddlerBackground
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (val screen = currentScreen) {
                    ScreenDestination.Home -> HomeScreen(viewModel = viewModel)
                    ScreenDestination.Learn -> LearnScreen(viewModel = viewModel)
                    ScreenDestination.Play -> PlayScreen(viewModel = viewModel)
                    ScreenDestination.Parent -> ParentDashboardScreen(viewModel = viewModel)
                    is ScreenDestination.LessonDetail -> DetailLessonScreen(
                        moduleType = screen.moduleType,
                        initialIndex = screen.initialIndex,
                        viewModel = viewModel,
                        onBack = { viewModel.navigateTo(ScreenDestination.Learn) }
                    )
                    is ScreenDestination.ActivityFlow -> ActivityScreen(
                        activityType = screen.activityType,
                        viewModel = viewModel,
                        onBack = { viewModel.navigateTo(ScreenDestination.Play) }
                    )
                    ScreenDestination.MemoryGame -> MemoryGameScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.navigateTo(ScreenDestination.Play) }
                    )
                    ScreenDestination.Quiz -> QuizScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.navigateTo(ScreenDestination.Play) }
                    )
                    ScreenDestination.Achievements -> AchievementsScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.navigateTo(ScreenDestination.Home) }
                    )
                    ScreenDestination.ParentSettings -> ParentSettingsScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.navigateTo(ScreenDestination.Parent) }
                    )
                    ScreenDestination.AboutUs -> AboutUsScreen(
                        viewModel = viewModel,
                        onBack = { viewModel.navigateTo(ScreenDestination.Parent) }
                    )
                }
            }
        }
    }

    // Parent Gate Security Challenge Dialog
    ParentGateDialog(
        isOpen = isParentGateOpen,
        onSuccess = { viewModel.onParentGateSuccess() },
        onDismiss = { viewModel.onParentGateDismiss() }
    )
}

@Composable
private fun DrawerContent(
    onItemClick: (ScreenDestination) -> Unit,
    onCloseDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Drawer Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(ToddlerCoralLight),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "⭐", fontSize = 28.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Toddler Hub",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = ToddlerCoral
                )
                Text(
                    text = "Ages 2–5 Years",
                    fontSize = 12.sp,
                    color = ToddlerTextSecondary
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFF0F0F0))

        // SECTION: LEARN
        DrawerSectionHeader(title = "LEARN 📚", color = ToddlerBlue)
        DrawerItem(label = "ABC Learning", emoji = "🔤") {
            onItemClick(ScreenDestination.LessonDetail(ModuleType.ABC, 0))
        }
        DrawerItem(label = "Numbers & Counting", emoji = "🔢") {
            onItemClick(ScreenDestination.LessonDetail(ModuleType.NUMBERS, 0))
        }
        DrawerItem(label = "Colors World", emoji = "🎨") {
            onItemClick(ScreenDestination.LessonDetail(ModuleType.COLORS, 0))
        }
        DrawerItem(label = "Shapes & Puzzles", emoji = "🔷") {
            onItemClick(ScreenDestination.LessonDetail(ModuleType.SHAPES, 0))
        }
        DrawerItem(label = "Animals Safari", emoji = "🦁") {
            onItemClick(ScreenDestination.LessonDetail(ModuleType.ANIMALS, 0))
        }
        DrawerItem(label = "Fruits Basket", emoji = "🍓") {
            onItemClick(ScreenDestination.LessonDetail(ModuleType.FRUITS, 0))
        }
        DrawerItem(label = "Vehicles & Wheels", emoji = "🚗") {
            onItemClick(ScreenDestination.LessonDetail(ModuleType.VEHICLES, 0))
        }
        DrawerItem(label = "Sound Library", emoji = "🎵") {
            onItemClick(ScreenDestination.LessonDetail(ModuleType.SOUNDS, 0))
        }

        Spacer(modifier = Modifier.height(10.dp))

        // SECTION: PLAY
        DrawerSectionHeader(title = "PLAY 🎮", color = ToddlerGreen)
        DrawerItem(label = "Memory Games", emoji = "🃏") {
            onItemClick(ScreenDestination.MemoryGame)
        }
        DrawerItem(label = "Counting Balloons", emoji = "🎈") {
            onItemClick(ScreenDestination.ActivityFlow(ActivityType.COUNT_OBJECTS))
        }
        DrawerItem(label = "Letter Tap", emoji = "👆") {
            onItemClick(ScreenDestination.ActivityFlow(ActivityType.LETTER_TAP))
        }
        DrawerItem(label = "Shape Match", emoji = "🧩") {
            onItemClick(ScreenDestination.ActivityFlow(ActivityType.SHAPE_MATCH))
        }
        DrawerItem(label = "Color Tap", emoji = "🎯") {
            onItemClick(ScreenDestination.ActivityFlow(ActivityType.COLOR_TAP))
        }
        DrawerItem(label = "Visual Quizzes", emoji = "⭐") {
            onItemClick(ScreenDestination.Quiz)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // SECTION: PROGRESS
        DrawerSectionHeader(title = "PROGRESS 🏆", color = ToddlerOrange)
        DrawerItem(label = "Stars & Achievements", emoji = "🌟") {
            onItemClick(ScreenDestination.Achievements)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // SECTION: PARENT
        DrawerSectionHeader(title = "PARENT 🛡️", color = ToddlerPurple)
        DrawerItem(label = "Parent Dashboard", emoji = "📊") {
            onItemClick(ScreenDestination.Parent)
        }
        DrawerItem(label = "Parent Settings & Audio", emoji = "⚙️") {
            onItemClick(ScreenDestination.ParentSettings)
        }
        DrawerItem(label = "About Us & Developer", emoji = "ℹ️") {
            onItemClick(ScreenDestination.AboutUs)
        }
    }
}

@Composable
private fun DrawerSectionHeader(title: String, color: Color) {
    Text(
        text = title,
        fontWeight = FontWeight.Black,
        fontSize = 12.sp,
        color = color,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
    )
}

@Composable
private fun DrawerItem(
    label: String,
    emoji: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = emoji, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ToddlerTextPrimary
            )
        }
    }
}
