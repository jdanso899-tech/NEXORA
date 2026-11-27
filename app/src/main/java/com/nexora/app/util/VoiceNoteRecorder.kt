package com.nexora.app.util

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File

/**
 * Simple voice-note recorder.
 * Call start(context) → stop() to get the saved file path.
 */
class VoiceNoteRecorder {

    private var recorder: MediaRecorder? = null
    private var outputPath: String? = null

    fun start(context: Context): String? {
        return try {
            val dir = File(context.filesDir, "voice_notes").apply { mkdirs() }
            val file = File(dir, "note_${System.currentTimeMillis()}.m4a")
            outputPath = file.absolutePath

            recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(outputPath)
                prepare()
                start()
            }
            outputPath
        } catch (e: Exception) {
            Log.e("VoiceNoteRecorder", "Failed to start", e)
            null
        }
    }

    fun stop(): String? {
        return try {
            recorder?.apply {
                stop()
                release()
            }
            recorder = null
            outputPath
        } catch (e: Exception) {
            Log.e("VoiceNoteRecorder", "Failed to stop", e)
            null
        }
    }

    fun isRecording(): Boolean = recorder != null
}
