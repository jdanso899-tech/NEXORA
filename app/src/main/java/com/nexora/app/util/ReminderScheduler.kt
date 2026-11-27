package com.nexora.app.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.nexora.app.data.model.Assignment

/**
 * Schedules exact alarms that fire the ReminderReceiver.
 * The receiver will then use TtsHelper to speak the assignment name + time left.
 */
object ReminderScheduler {

    fun schedule(context: Context, assignment: Assignment, leadMinutes: Long = 30) {
        val triggerAt = assignment.dueDateTime - (leadMinutes * 60 * 1000)
        if (triggerAt <= System.currentTimeMillis()) return

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(EXTRA_TITLE, assignment.title)
            putExtra(EXTRA_DUE, assignment.dueDateTime)
            putExtra(EXTRA_ID, assignment.id)
        }

        val pending = PendingIntent.getBroadcast(
            context,
            assignment.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pending)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAt, pending)
        }
    }

    fun cancel(context: Context, assignmentId: String) {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pending = PendingIntent.getBroadcast(
            context,
            assignmentId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pending)
    }

    const val EXTRA_TITLE = "extra_title"
    const val EXTRA_DUE = "extra_due"
    const val EXTRA_ID = "extra_id"
}
