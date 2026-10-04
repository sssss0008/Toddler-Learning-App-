package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class ModuleType(
    val title: String,
    val subtitle: String,
    val emoji: String,
    val themeColor: Color,
    val lightColor: Color
) {
    ABC("ABC Learning", "Letters, Sounds & Phonics", "🔤", ColorAbc, ToddlerCoralLight),
    NUMBERS("Numbers & Counting", "Learn 1–20 & Quantities", "🔢", ColorNumbers, ToddlerBlueLight),
    COLORS("Color World", "Identify & Mix Colors", "🎨", ColorColors, ToddlerYellowLight),
    SHAPES("Shapes & Puzzles", "Geometric & Real-world Shapes", "🔷", ColorShapes, ToddlerGreenLight),
    ANIMALS("Animal Kingdom", "Farm, Jungle, Ocean & Sounds", "🦁", ColorAnimals, ToddlerOrangeLight),
    FRUITS("Fruit Basket", "Delicious Fruits & Colors", "🍓", ColorFruits, ToddlerPinkLight),
    VEHICLES("Vehicles & Wheels", "Cars, Planes, Trains & Sirens", "🚗", ColorVehicles, ToddlerBlueLight),
    SOUNDS("Sound Safari", "Identify Everyday & Nature Sounds", "🎵", ColorSounds, ToddlerPurpleLight)
}

data class LessonItem(
    val id: String,
    val moduleType: ModuleType,
    val title: String,
    val subtitle: String,
    val visualEmoji: String,
    val speechText: String,
    val soundPrompt: String = "",
    val pronunciation: String = "",
    val category: String = "General",
    val realWorldExample: String = "",
    val accentColor: Color = ToddlerCoral,
    val isPremium: Boolean = false
)

enum class ActivityType(val title: String, val description: String, val emoji: String) {
    LETTER_TAP("Letter Tap", "Tap the requested letter", "👆"),
    LETTER_MATCH("Letter Match", "Match capital & small letters", "🔠"),
    LETTER_TRACE("Letter Tracing", "Trace letters with your finger", "✏️"),
    BEGINNING_SOUND("Beginning Sounds", "Which word starts with this letter?", "👂"),
    COUNT_OBJECTS("Count Objects", "Tap and count items together", "🎈"),
    FIND_NUMBER("Find Number", "Find the matching number", "🔢"),
    NUMBER_SEQUENCE("Number Sequence", "What number comes next?", "➡️"),
    MORE_OR_LESS("More or Less", "Compare which group has more", "⚖️"),
    COLOR_TAP("Color Tap", "Tap the matching color", "🎯"),
    COLOR_SORT("Color Sorting", "Sort objects into matching baskets", "🧺"),
    SHAPE_MATCH("Shape Matching", "Match shapes to real-world objects", "🧩"),
    GUESS_SOUND("Guess the Sound", "Listen and pick what made the sound", "🔊"),
    PICTURE_MEMORY("Picture Memory", "Find matching cute picture pairs", "🃏"),
    QUICK_QUIZ("Visual Quiz", "Voice-guided fun learning quiz", "⭐")
}

data class DailyAdventureStep(
    val id: String,
    val title: String,
    val moduleType: ModuleType,
    val iconEmoji: String,
    val isCompleted: Boolean = false
)

data class ChildProfileData(
    val name: String = "Little Explorer",
    val ageRange: String = "3–4",
    val avatarId: String = "bear",
    val totalStars: Int = 12,
    val journeyStage: String = "Curious Explorer"
)

data class AchievementData(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean,
    val unlockedDate: String = ""
)

data class ParentSettingsData(
    val narrationEnabled: Boolean = true,
    val soundEffectsEnabled: Boolean = true,
    val musicEnabled: Boolean = true,
    val sessionTimerMinutes: Int = 20,
    val isSubscribed: Boolean = false,
    val subscriptionPlan: String = "Free Explorer",
    val childAgeRange: String = "3–4"
)
