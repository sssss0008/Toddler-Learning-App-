package com.example.audio

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class SoundManager(private val context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private var toneGenerator: ToneGenerator? = null

    var isNarrationEnabled = true
    var isSoundEffectsEnabled = true
    var isMuted = false

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)
        } catch (e: Exception) {
            Log.e("SoundManager", "Error initializing audio components", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                isTtsReady = true
                tts?.setPitch(1.15f) // Friendly preschool voice pitch
                tts?.setSpeechRate(0.85f) // Clear, slower toddler pacing
            }
        }
    }

    fun speak(text: String, force: Boolean = false) {
        if (isMuted) return
        if (!isNarrationEnabled && !force) return
        if (isTtsReady) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "toddler_tts_${System.currentTimeMillis()}")
        }
    }

    fun stop() {
        tts?.stop()
    }

    fun playSuccessSound() {
        if (isMuted || !isSoundEffectsEnabled) return
        try {
            // Ascending sweet notification chime
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 120)
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 200)
            }, 100)
        } catch (e: Exception) {
            Log.e("SoundManager", "Tone error", e)
        }
    }

    fun playTryAgainSound() {
        if (isMuted || !isSoundEffectsEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_NACK, 180)
        } catch (e: Exception) {
            Log.e("SoundManager", "Tone error", e)
        }
    }

    fun playTapPop() {
        if (isMuted || !isSoundEffectsEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP2, 50)
        } catch (e: Exception) {
            Log.e("SoundManager", "Tone error", e)
        }
    }

    fun playStarSound() {
        if (isMuted || !isSoundEffectsEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE, 150)
        } catch (e: Exception) {
            Log.e("SoundManager", "Tone error", e)
        }
    }

    fun release() {
        tts?.stop()
        tts?.shutdown()
        toneGenerator?.release()
    }
}
