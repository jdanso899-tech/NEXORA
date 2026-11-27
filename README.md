# NEXORA

**Your AI-powered assignment companion for Android.**

Voice deadline reminders • Custom AI chatbot • Friends from contacts • Share & compare answers • Profile photo

---

## Quick start

1. Unzip and open the **NEXORA** folder in **Android Studio**.
2. Let Gradle sync (Android Studio will create the wrapper if needed).
3. Run on an emulator or device (API 26+).

---

## What’s included

| Feature | Status |
|---------|--------|
| Create / list / view / delete assignments | ✅ Room database |
| Save answer text on assignment | ✅ |
| Spoken due reminders (TTS) | ✅ AlarmManager + TextToSpeech |
| Voice note recorder utility | ✅ MediaRecorder helper ready |
| Bottom navigation | ✅ Home • Tasks • AI • Friends • Profile |
| Custom AI Chat UI | ✅ Offline smart replies (swap for real LLM) |
| Friends from phone contacts | ✅ ContactsHelper + Call / Text intents |
| Profile screen | ✅ Name, bio, photo placeholder |
| Compare Answers + “Ask AI” | ✅ Side-by-side demo |
| Dark Material 3 theme + app icon | ✅ |

---

## Project structure

```
app/src/main/java/com/nexora/app/
├── MainActivity.kt                 # Bottom nav + host
├── NexoraApp.kt                    # Application + DB
├── data/
│   ├── model/Models.kt
│   ├── local/AppDatabase.kt, Daos.kt
│   └── repository/AssignmentRepository.kt
├── ui/
│   ├── theme/Theme.kt
│   ├── navigation/NavGraph.kt
│   └── screens/                    # All main screens
├── viewmodel/AssignmentViewModel.kt
└── util/
    ├── TtsHelper.kt                # Spoken reminders
    ├── ReminderScheduler.kt
    ├── ReminderReceiver.kt
    ├── VoiceNoteRecorder.kt        # Record .m4a notes
    └── ContactsHelper.kt           # Load phone contacts
```

---

## Key utilities ready for expansion

- **TtsHelper** – speaks “Math homework is due in 30 minutes”
- **ReminderScheduler** – exact alarms before due time
- **VoiceNoteRecorder** – start/stop → returns file path
- **ContactsHelper** – reads contacts (needs READ_CONTACTS)

---

## Connecting a real AI (recommended next step)

In `AiChatScreen.kt`, replace the `generateLocalReply()` function with a network call to:

- xAI Grok API
- Anthropic Claude
- OpenAI GPT

Keep the same `ChatMessage` list and the UI will work unchanged. Streaming responses give the best experience.

---

## Permissions

- `RECORD_AUDIO` – voice notes & speech
- `READ_CONTACTS` – friends list
- `POST_NOTIFICATIONS` – deadline alerts
- `SCHEDULE_EXACT_ALARM` – precise spoken reminders
- `INTERNET` – AI + future sync
- Camera / media – profile photo

---

## Related deliverables

- **NEXORA_Product_Presentation.pptx** – 11-slide pitch deck
- **NEXORA_Project_Documentation.docx** – full product & technical spec

---

Built as a solid, runnable foundation for the complete NEXORA product.
