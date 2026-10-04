package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learning_progress")
data class LearningProgressEntity(
    @PrimaryKey val moduleId: String,
    val completedLessons: Int,
    val totalLessons: Int,
    val starsEarned: Int,
    val correctAnswers: Int,
    val lastPracticedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean,
    val unlockedAt: Long = 0L
)

@Entity(tableName = "child_profile")
data class ChildProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Little Learner",
    val ageRange: String = "3–4",
    val avatarId: String = "star",
    val totalStars: Int = 15,
    val journeyStage: String = "Curious Explorer"
)
