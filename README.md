StudyBuddy is an Android app that helps students learn faster with flashcards and quick quizzes. Create cards with subjects, difficulty, hints, and answers; run adaptive quizzes; and track how you’re doing over time.

✨ Key Features

Flashcards CRUD – Create, edit, view, and delete cards (subject, difficulty: Easy/Medium/Hard, optional hint, answer).

Quizzes – Shuffle questions, limit quiz length, instant feedback, and end-of-quiz results (score, correctness, hint usage).

Progress Awareness – Cards store basic answer history so users can see which items need more practice.

Multi-User Ready – Simple user selection/creation screen for shared devices.

Clean Architecture – Presentation (Activities) → Business (Use Cases) → Persistence (HSQLDB or in-memory stubs).

🧱 Architecture (High-Level)
Presentation (Android Activities)
  ├── StartupActivity, SelectUserActivity, CreateUserActivity
  ├── HomeActivity, ViewFlashcardsActivity, CreateFlashcardActivity, EditFlashcardActivity
  └── QuizActivity, QuizResults (layout)

Business (Use Cases)
  ├── AccessUsers
  ├── AccessFlashcards
  └── QuizManager (shuffle, next/prev, max questions)

Persistence
  ├── Interfaces: UserTable, FlashcardTable
  ├── Stubs: UserTableStub, FlashcardTableStub (fast tests/dev)
  └── HSQLDB: UserTableHSQLDB, FlashcardTableHSQLDB (+ PersistenceException)

Domain Objects
  ├── User
  └── Flashcard

🛠️ Tech Stack

Android (Java, Gradle, AndroidX)

minSdk 24, targetSdk 35

UI: ConstraintLayout, Material components

DB: HSQLDB (switchable), or Stub implementations for testing/dev

Testing: JUnit unit tests, basic instrumentation/acceptance tests

🚀 Getting Started

Open in Android Studio (Giraffe+ recommended).

Let Gradle sync; ensure JDK 11 is configured for the project.

Run the app on an emulator or device (Android 7.0+).

(Optional) Switch persistence:

Stub (default for tests/dev) – uses in-memory tables.

HSQLDB – enable HSQLDB tables via Services wiring for persistent storage.

🧪 Tests

Unit tests: app/src/test/java/... (run via Gradle or Android Studio).

Instrumentation/acceptance: app/src/androidTest/... (run on device/emulator).
