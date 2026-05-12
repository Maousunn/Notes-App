<p align="center">
  <img src="app_logo.png" alt="Notes App Logo" width="120" height="120">
</p>

<h1 align="center">📝 Notes</h1>

<p align="center">
  A clean, professional Android notes application built with Kotlin, Room Database, and Material Design 3.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green?style=flat-square&logo=android" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Kotlin-purple?style=flat-square&logo=kotlin" alt="Language">
  <img src="https://img.shields.io/badge/Min%20SDK-24-blue?style=flat-square" alt="Min SDK">
  <img src="https://img.shields.io/badge/Architecture-MVVM-orange?style=flat-square" alt="Architecture">
</p>

---

## ✨ Features

- **Create, Edit & Delete Notes** — Full CRUD operations with a clean UI
- **Room Database** — Persistent local storage that survives app restarts
- **Filter Notes** — Toggle between All, Completed, and Pending notes
- **Mark as Complete** — Checkbox toggle with visual strikethrough feedback
- **Splash Screen** — Professional launch experience using AndroidX SplashScreen API
- **Material Design 3** — Modern, clean UI following Google's design guidelines
- **Dark Mode Support** — Automatic light/dark theme based on system settings
- **Empty State** — Friendly UI when no notes exist
- **Timestamps** — Each note displays when it was created/updated

---

## 📸 Screenshots

| Splash Screen | Home (Light) | Home (Dark) |
|:---:|:---:|:---:|
| ![Splash Screen](screenshots/splash.png) | ![Home Light](screenshots/home_light.png) | ![Home Dark](screenshots/home_dark.png) |

| Add Note | Edit Note | Empty State |
|:---:|:---:|:---:|
| ![Add Note](screenshots/add_note.png) | ![Edit Note](screenshots/edit_note.png) | ![Empty State](screenshots/empty_state.png) |

> **Note:** Replace the screenshot placeholders above with actual screenshots from your device.

---

## 🏗️ Architecture

The app follows the **MVVM (Model-View-ViewModel)** architecture pattern:

```
com.example.notes/
├── data/
│   ├── Note.kt              # Room Entity (data model)
│   ├── NoteDao.kt            # Data Access Object (database queries)
│   ├── NoteDatabase.kt       # Room Database (singleton)
│   └── NoteRepository.kt     # Repository (clean API for ViewModel)
├── ui/
│   ├── SplashActivity.kt     # Splash screen
│   ├── MainActivity.kt       # Main notes list screen
│   ├── AddEditNoteActivity.kt # Add/Edit note screen
│   └── NotesAdapter.kt       # RecyclerView adapter with DiffUtil
├── viewmodel/
│   └── NotesViewModel.kt     # ViewModel + ViewModelFactory
└── NotesApplication.kt       # Application class (DB initialization)
```

### Data Flow

```
UI (Activity) → ViewModel → Repository → DAO → Room Database
       ↑                                           |
       └──────── LiveData (auto-updates) ──────────┘
```

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Language** | Kotlin |
| **UI Framework** | Android XML + Material Design 3 |
| **Database** | Room (SQLite wrapper) |
| **Architecture** | MVVM |
| **Async** | Kotlin Coroutines |
| **Reactive** | LiveData |
| **DI** | Manual (Application class) |
| **Build System** | Gradle (Kotlin DSL) |

---

## 📦 Dependencies

```kotlin
// Core Android
androidx.core:core-ktx
androidx.appcompat:appcompat
com.google.android.material:material

// Room Database
androidx.room:room-runtime
androidx.room:room-ktx
androidx.room:room-compiler (KSP)

// Lifecycle
androidx.lifecycle:lifecycle-viewmodel-ktx
androidx.lifecycle:lifecycle-livedata-ktx

// Coroutines
org.jetbrains.kotlinx:kotlinx-coroutines-android

// UI Components
androidx.recyclerview:recyclerview
androidx.cardview:cardview
androidx.core:core-splashscreen
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 11+
- Android SDK 24+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/Notes.git
   ```

2. **Open in Android Studio**
   - File → Open → Select the project directory

3. **Sync Gradle**
   - Android Studio will prompt you to sync — click "Sync Now"

4. **Run the app**
   - Select a device/emulator and click ▶️ Run

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

<p align="center">
  Made with ❤️ using Kotlin & Material Design 3
</p>
