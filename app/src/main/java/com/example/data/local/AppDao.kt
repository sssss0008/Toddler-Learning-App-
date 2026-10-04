package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM learning_progress")
    fun getAllProgress(): Flow<List<LearningProgressEntity>>

    @Query("SELECT * FROM learning_progress WHERE moduleId = :moduleId")
    suspend fun getProgressForModule(moduleId: String): LearningProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: LearningProgressEntity)

    @Query("UPDATE learning_progress SET completedLessons = 0, starsEarned = 0, correctAnswers = 0")
    suspend fun resetAllProgress()
}

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<AchievementEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(achievements: List<AchievementEntity>)

    @Query("UPDATE achievements SET isUnlocked = 1, unlockedAt = :timestamp WHERE id = :id")
    suspend fun unlockAchievement(id: String, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE achievements SET isUnlocked = 0, unlockedAt = 0")
    suspend fun resetAchievements()
}

@Dao
interface ProfileDao {
    @Query("SELECT * FROM child_profile WHERE id = 1")
    fun getProfile(): Flow<ChildProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: ChildProfileEntity)

    @Query("UPDATE child_profile SET totalStars = totalStars + :stars WHERE id = 1")
    suspend fun addStars(stars: Int)
}
