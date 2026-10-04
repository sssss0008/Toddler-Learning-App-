package com.example

import com.example.data.model.CurriculumData
import com.example.data.model.ModuleType
import com.example.data.repository.ToddlerRepository
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testCurriculumModulesExist() {
        assertEquals(8, ModuleType.entries.size)
        assertTrue(CurriculumData.abcLessons.isNotEmpty())
        assertEquals(26, CurriculumData.abcLessons.size)
        assertTrue(CurriculumData.numberLessons.isNotEmpty())
        assertTrue(CurriculumData.colorLessons.isNotEmpty())
        assertTrue(CurriculumData.shapeLessons.isNotEmpty())
        assertTrue(CurriculumData.animalLessons.isNotEmpty())
        assertTrue(CurriculumData.fruitLessons.isNotEmpty())
        assertTrue(CurriculumData.vehicleLessons.isNotEmpty())
        assertTrue(CurriculumData.soundLessons.isNotEmpty())
    }

    @Test
    fun testJourneyStages() {
        assertEquals("Little Learner", ToddlerRepository.calculateJourneyStage(5))
        assertEquals("Curious Explorer", ToddlerRepository.calculateJourneyStage(20))
        assertEquals("Happy Learner", ToddlerRepository.calculateJourneyStage(45))
        assertEquals("Smart Explorer", ToddlerRepository.calculateJourneyStage(80))
        assertEquals("Learning Star", ToddlerRepository.calculateJourneyStage(120))
    }
}
