package com.example.data.repository

import com.example.data.local.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ToddlerRepository(
    private val database: AppDatabase
) {
    private val progressDao = database.progressDao()
    private val achievementDao = database.achievementDao()
    private val profileDao = database.profileDao()

    val allProgress: Flow<List<LearningProgressEntity>> = progressDao.getAllProgress()
    val allAchievements: Flow<List<AchievementEntity>> = achievementDao.getAllAchievements()
    val childProfile: Flow<ChildProfileEntity> = profileDao.getProfile().map {
        it ?: ChildProfileEntity(
            id = 1,
            name = "Leo",
            ageRange = "3–4",
            avatarId = "star",
            totalStars = 15,
            journeyStage = "Curious Explorer"
        )
    }

    suspend fun initializeDefaultsIfNeeded() {
        // Pre-populate progress for all 8 modules
        for (module in ModuleType.entries) {
            val existing = progressDao.getProgressForModule(module.name)
            if (existing == null) {
                val total = CurriculumData.getLessonsForModule(module).size
                val initialCompleted = when (module) {
                    ModuleType.ABC -> 5
                    ModuleType.NUMBERS -> 3
                    ModuleType.COLORS -> 6
                    ModuleType.SHAPES -> 2
                    ModuleType.ANIMALS -> 4
                    else -> 1
                }
                val initialStars = initialCompleted * 2
                progressDao.upsertProgress(
                    LearningProgressEntity(
                        moduleId = module.name,
                        completedLessons = initialCompleted,
                        totalLessons = total,
                        starsEarned = initialStars,
                        correctAnswers = initialCompleted * 3
                    )
                )
            }
        }

        // Pre-populate achievements
        val defaultAchievements = listOf(
            AchievementEntity("first_lesson", "First Lesson", "Complete your first learning activity!", "⭐", true, System.currentTimeMillis()),
            AchievementEntity("abc_explorer", "ABC Explorer", "Explore multiple letters and sounds!", "🔤", true, System.currentTimeMillis()),
            AchievementEntity("counting_star", "Counting Star", "Count objects up to 5 and beyond!", "🔢", true, System.currentTimeMillis()),
            AchievementEntity("color_explorer", "Color Explorer", "Identify 5 or more cheerful colors!", "🎨", true, System.currentTimeMillis()),
            AchievementEntity("animal_friend", "Animal Friend", "Discover 5 wonderful animal sounds!", "🦁", false),
            AchievementEntity("shape_explorer", "Shape Explorer", "Match shapes to real-world objects!", "🔷", false),
            AchievementEntity("memory_star", "Memory Master", "Complete a card memory matching game!", "🃏", false),
            AchievementEntity("learning_star", "Super Star", "Complete activities across all modules!", "🏆", false)
        )
        achievementDao.insertAll(defaultAchievements)
    }

    suspend fun recordLessonCompleted(moduleType: ModuleType, starsEarned: Int = 1) {
        val current = progressDao.getProgressForModule(moduleType.name)
        val total = CurriculumData.getLessonsForModule(moduleType).size
        val newCompleted = ((current?.completedLessons ?: 0) + 1).coerceAtMost(total)
        val newStars = (current?.starsEarned ?: 0) + starsEarned
        val newCorrect = (current?.correctAnswers ?: 0) + 1

        progressDao.upsertProgress(
            LearningProgressEntity(
                moduleId = moduleType.name,
                completedLessons = newCompleted,
                totalLessons = total,
                starsEarned = newStars,
                correctAnswers = newCorrect,
                lastPracticedAt = System.currentTimeMillis()
            )
        )

        profileDao.addStars(starsEarned)
        checkAndUnlockAchievements(moduleType, newCompleted, newStars)
    }

    suspend fun addBonusStars(stars: Int) {
        profileDao.addStars(stars)
    }

    private suspend fun checkAndUnlockAchievements(moduleType: ModuleType, completed: Int, stars: Int) {
        if (completed >= 5) {
            when (moduleType) {
                ModuleType.ABC -> achievementDao.unlockAchievement("abc_explorer")
                ModuleType.NUMBERS -> achievementDao.unlockAchievement("counting_star")
                ModuleType.COLORS -> achievementDao.unlockAchievement("color_explorer")
                ModuleType.ANIMALS -> achievementDao.unlockAchievement("animal_friend")
                ModuleType.SHAPES -> achievementDao.unlockAchievement("shape_explorer")
                else -> {}
            }
        }
    }

    suspend fun unlockMemoryAchievement() {
        achievementDao.unlockAchievement("memory_star")
    }

    suspend fun updateProfile(name: String, ageRange: String, avatarId: String) {
        profileDao.upsertProfile(
            ChildProfileEntity(
                id = 1,
                name = name,
                ageRange = ageRange,
                avatarId = avatarId,
                totalStars = 20,
                journeyStage = calculateJourneyStage(20)
            )
        )
    }

    suspend fun resetAllData() {
        progressDao.resetAllProgress()
        achievementDao.resetAchievements()
        profileDao.upsertProfile(
            ChildProfileEntity(
                id = 1,
                name = "Little Explorer",
                ageRange = "3–4",
                avatarId = "star",
                totalStars = 0,
                journeyStage = "Little Learner"
            )
        )
    }

    companion object {
        fun calculateJourneyStage(stars: Int): String {
            return when {
                stars < 15 -> "Little Learner"
                stars < 35 -> "Curious Explorer"
                stars < 65 -> "Happy Learner"
                stars < 100 -> "Smart Explorer"
                else -> "Learning Star"
            }
        }
    }
}
