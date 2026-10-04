package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SoundManager
import com.example.data.local.AppDatabase
import com.example.data.local.ChildProfileEntity
import com.example.data.local.LearningProgressEntity
import com.example.data.model.*
import com.example.data.repository.ToddlerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ScreenDestination {
    data object Home : ScreenDestination()
    data object Learn : ScreenDestination()
    data object Play : ScreenDestination()
    data object Parent : ScreenDestination()
    data class LessonDetail(val moduleType: ModuleType, val initialIndex: Int = 0) : ScreenDestination()
    data class ActivityFlow(val activityType: ActivityType) : ScreenDestination()
    data object MemoryGame : ScreenDestination()
    data object Quiz : ScreenDestination()
    data object Achievements : ScreenDestination()
    data object ParentSettings : ScreenDestination()
    data object AboutUs : ScreenDestination()
}

class ToddlerViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val repository = ToddlerRepository(database)
    val soundManager = SoundManager(application)

    private val _currentScreen = MutableStateFlow<ScreenDestination>(ScreenDestination.Home)
    val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

    private val _currentBottomTab = MutableStateFlow(0) // 0: Home, 1: Learn, 2: Play, 3: Parent
    val currentBottomTab: StateFlow<Int> = _currentBottomTab.asStateFlow()

    private val _parentGateOpen = MutableStateFlow(false)
    val parentGateOpen: StateFlow<Boolean> = _parentGateOpen.asStateFlow()
    private var pendingParentAction: (() -> Unit)? = null

    private val _parentSettings = MutableStateFlow(
        ParentSettingsData(
            narrationEnabled = true,
            soundEffectsEnabled = true,
            musicEnabled = true,
            sessionTimerMinutes = 20,
            isSubscribed = false,
            subscriptionPlan = "Free Explorer",
            childAgeRange = "3–4"
        )
    )
    val parentSettings: StateFlow<ParentSettingsData> = _parentSettings.asStateFlow()

    private val _dailyAdventure = MutableStateFlow(
        listOf(
            DailyAdventureStep("step_abc", "Learn Letter A", ModuleType.ABC, "🍎", true),
            DailyAdventureStep("step_col", "Explore Red Color", ModuleType.COLORS, "🔴", true),
            DailyAdventureStep("step_ani", "Meet the Lion", ModuleType.ANIMALS, "🦁", false),
            DailyAdventureStep("step_mem", "Play Memory Game", ModuleType.SHAPES, "🃏", false)
        )
    )
    val dailyAdventure: StateFlow<List<DailyAdventureStep>> = _dailyAdventure.asStateFlow()

    val childProfile: StateFlow<ChildProfileEntity> = repository.childProfile
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ChildProfileEntity(name = "Leo", ageRange = "3–4", avatarId = "star", totalStars = 15)
        )

    val allProgress: StateFlow<List<LearningProgressEntity>> = repository.allProgress
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val allAchievements = repository.allAchievements
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        viewModelScope.launch {
            repository.initializeDefaultsIfNeeded()
        }
    }

    fun navigateTo(screen: ScreenDestination) {
        soundManager.playTapPop()
        _currentScreen.value = screen
        when (screen) {
            ScreenDestination.Home -> _currentBottomTab.value = 0
            ScreenDestination.Learn -> _currentBottomTab.value = 1
            ScreenDestination.Play -> _currentBottomTab.value = 2
            ScreenDestination.Parent -> _currentBottomTab.value = 3
            else -> {}
        }
    }

    fun selectBottomTab(tabIndex: Int) {
        soundManager.playTapPop()
        when (tabIndex) {
            0 -> {
                _currentBottomTab.value = 0
                _currentScreen.value = ScreenDestination.Home
            }
            1 -> {
                _currentBottomTab.value = 1
                _currentScreen.value = ScreenDestination.Learn
            }
            2 -> {
                _currentBottomTab.value = 2
                _currentScreen.value = ScreenDestination.Play
            }
            3 -> {
                // Parent tab protected with Parent Gate
                requestParentAccess {
                    _currentBottomTab.value = 3
                    _currentScreen.value = ScreenDestination.Parent
                }
            }
        }
    }

    fun requestParentAccess(action: () -> Unit) {
        pendingParentAction = action
        _parentGateOpen.value = true
    }

    fun onParentGateSuccess() {
        _parentGateOpen.value = false
        pendingParentAction?.invoke()
        pendingParentAction = null
    }

    fun onParentGateDismiss() {
        _parentGateOpen.value = false
        pendingParentAction = null
    }

    fun speak(text: String, force: Boolean = false) {
        soundManager.speak(text, force)
    }

    fun onLessonCompleted(moduleType: ModuleType, starsEarned: Int = 1) {
        viewModelScope.launch {
            repository.recordLessonCompleted(moduleType, starsEarned)
            soundManager.playSuccessSound()
            soundManager.speak("Wonderful job! You earned a shiny golden star!")
        }
    }

    fun completeDailyStep(stepId: String) {
        val updated = _dailyAdventure.value.map {
            if (it.id == stepId) it.copy(isCompleted = true) else it
        }
        _dailyAdventure.value = updated
        val allDone = updated.all { it.isCompleted }
        if (allDone) {
            viewModelScope.launch {
                repository.addBonusStars(3)
                soundManager.playSuccessSound()
                soundManager.speak("Hooray! Today's learning adventure is complete! You won 3 stars!")
            }
        }
    }

    fun onMemoryGameWon() {
        viewModelScope.launch {
            repository.unlockMemoryAchievement()
            repository.addBonusStars(2)
            soundManager.playSuccessSound()
            soundManager.speak("Memory master! You found all the matching cards!")
        }
    }

    fun updateChildProfile(name: String, ageRange: String, avatarId: String) {
        viewModelScope.launch {
            repository.updateProfile(name, ageRange, avatarId)
        }
    }

    fun updateParentSettings(
        narration: Boolean,
        soundEffects: Boolean,
        music: Boolean,
        sessionTimer: Int,
        ageRange: String
    ) {
        _parentSettings.value = _parentSettings.value.copy(
            narrationEnabled = narration,
            soundEffectsEnabled = soundEffects,
            musicEnabled = music,
            sessionTimerMinutes = sessionTimer,
            childAgeRange = ageRange
        )
        soundManager.isNarrationEnabled = narration
        soundManager.isSoundEffectsEnabled = soundEffects
    }

    fun upgradeSubscription(planName: String) {
        _parentSettings.value = _parentSettings.value.copy(
            isSubscribed = true,
            subscriptionPlan = planName
        )
        soundManager.playSuccessSound()
    }

    fun resetAllLearningData() {
        viewModelScope.launch {
            repository.resetAllData()
            _dailyAdventure.value = _dailyAdventure.value.map { it.copy(isCompleted = false) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
    }
}
