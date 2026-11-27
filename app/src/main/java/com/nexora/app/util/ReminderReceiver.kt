package com.nexora.app.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Receives the alarm and speaks the assignment reminder.
 */
class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(ReminderScheduler.EXTRA_TITLE) ?: return
        val due = intent.getLongExtra(ReminderScheduler.EXTRA_DUE, 0L)
        val minutesLeft = ((due - System.currentTimeMillis()) / 60000).coerceAtLeast(0)

        Log.d("ReminderReceiver", "Speaking reminder for $title ($minutesLeft min left)")

        val tts = TtsHelper(context)
        // Small delay so TTS engine can initialise
        android.os.Handler(context.mainLooper).postDelayed({
            tts.speakDueReminder(title, minutesLeft)
        }, 800)
    }
}
