<p align="center">
  <img src="app/src/main/res/drawable/notes_logo.png" alt="Notes App Logo" width="120">
</p>

<h1 align="center">Notes</h1>

<p align="center">
  A clean Android notes application built with Kotlin, Room Database, MVVM, and Material Design.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green?style=flat-square&logo=android" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Kotlin-purple?style=flat-square&logo=kotlin" alt="Language">
  <img src="https://img.shields.io/badge/Min%20SDK-24-blue?style=flat-square" alt="Min SDK">
  <img src="https://img.shields.io/badge/Architecture-MVVM-orange?style=flat-square" alt="Architecture">
</p>

## Screenshots

<p align="center">
  <img src="screenshots/Splash%20screen.jpg" alt="Splash screen" width="220">
  <img src="screenshots/App%20home%20UI.jpg" alt="Home screen" width="220">
  <img src="screenshots/App%20add%20note%20screen.jpg" alt="Add note screen" width="220">
</p>

<p align="center">
  <img src="screenshots/Note%20pending%20UI.jpg" alt="Pending note screen" width="220">
  <img src="screenshots/Note%20completed%20UI.jpg" alt="Completed note screen" width="220">
</p>

## Features

- Create, edit, and delete notes
- Save notes locally with Room Database
- Filter notes by all, completed, and pending states
- Mark notes as complete with checkbox feedback
- Launch experience using AndroidX SplashScreen
- Material Design based UI
- Light and dark theme support
- Empty state for a clean first-run experience
- Created and updated timestamps for each note

## Architecture

The app follows the MVVM pattern:

```text
com.example.notes/
|-- data/
|   |-- Note.kt
|   |-- NoteDao.kt
|   |-- NoteDatabase.kt
|   `-- NoteRepository.kt
|-- ui/
|   |-- SplashActivity.kt
|   |-- MainActivity.kt
|   |-- AddEditNoteActivity.kt
|   `-- NotesAdapter.kt
|-- viewmodel/
|   `-- NotesViewModel.kt
`-- NotesApplication.kt
```

```text
Activity -> ViewModel -> Repository -> DAO -> Room Database
   ^                                           |
   `--------------- LiveData updates ----------`
```

## Tech Stack

| Component | Technology |
| --- | --- |
| Language | Kotlin |
| UI | Android XML, Material Design |
| Database | Room |
| Architecture | MVVM |
| Async | Kotlin Coroutines |
| Reactive data | LiveData |
| Build system | Gradle Kotlin DSL |

## Requirements

- Android Studio
- JDK 11 or newer
- Android SDK 24 or newer

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/YOUR_USERNAME/Notes.git
   ```

2. Open the project in Android Studio.

3. Sync Gradle.

4. Run the app on an emulator or Android device.

## Build

Run unit tests:

```bash
./gradlew test
```

Build a debug APK:

```bash
./gradlew assembleDebug
```

## Project Info

- Package: `com.example.notes`
- Compile SDK: `35`
- Target SDK: `34`
- Minimum SDK: `24`
- Version: `1.0`
