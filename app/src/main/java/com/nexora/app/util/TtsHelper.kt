package com.nexora.app.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

/**
 * Simple Text-to-Speech helper used for spoken assignment reminders
 * and optional AI voice replies.
 */
class TtsHelper(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var ready = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.getDefault()
            ready = true
        } else {
            Log.e("TtsHelper", "TTS init failed")
        }
    }

    fun speak(text: String) {
        if (ready) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "nexora_utterance")
        }
    }

    /**
     * Speaks a due-soon reminder, e.g.
     * "Math homework is due in 30 minutes."
     */
    fun speakDueReminder(assignmentTitle: String, minutesLeft: Long) {
        val timePhrase = when {
            minutesLeft <= 1 -> "is due now"
            minutesLeft < 60 -> "is due in $minutesLeft minutes"
            else -> {
                val hours = minutesLeft / 60
                "is due in $hours hours"
            }
        }
        speak("$assignmentTitle $timePhrase")
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        ready = false
    }
}
