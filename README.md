# TodoListApp

[![Android CI](https://github.com/tecruz/TodoListApp/actions/workflows/android_ci.yml/badge.svg)](https://github.com/tecruz/TodoListApp/actions/workflows/android_ci.yml)
[![codecov](https://codecov.io/gh/tecruz/TodoListApp/graph/badge.svg?token=your_codecov_token)](https://codecov.io/gh/tecruz/TodoListApp)

A simple to-do list application for Android, built with modern Android development tools and best practices.

## ✨ Features

*   Add, edit, and delete to-do items.
*   Mark to-do items as complete.
*   Clean, intuitive user interface built with Jetpack Compose.

## 📸 Screenshots

<!-- Add your screenshots here -->
<p>
  <img width="220" height="600" alt="screenshot_1" src="https://github.com/user-attachments/assets/0bce6897-4c46-43e3-9db2-3f34bf5a7bdd" />
</p>

## 🛠️ Tech Stack & Tools

*   **Language:** [Kotlin](https://kotlinlang.org/)
*   **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
*   **Database:** [Room](https://developer.android.com/training/data-storage/room)
*   **Static Analysis:** [Detekt](https://detekt.dev/)
*   **Code Style:** [KtLint](https://ktlint.github.io/)
*   **Test Coverage:** [Kover](https://github.com/Kotlin/kotlinx-kover)
*   **CI/CD:** [GitHub Actions](https://github.com/features/actions)

## 🚀 Setup

To build and run the project, follow these steps:

1.  Clone the repository:
    ```bash
    git clone https://github.com/tecruz/TodoListApp.git
    ```
2.  Open the project in Android Studio.
3.  Let Gradle sync and download the necessary dependencies.
4.  Run the application on an emulator or a physical device.

## ✅ Running Checks

This project is equipped with several tools to ensure code quality. You can run them locally using the following commands:

*   **KtLint (Check formatting):**
    ```bash
    ./gradlew ktlintCheck
    ```

*   **Detekt (Static analysis):**
    ```bash
    ./gradlew detekt
    ```

*   **Unit Tests:**
    ```bash
    ./gradlew test
    ```

*   **Instrumentation Tests:**
    ```bash
    ./gradlew connectedCheck
    ```

*   **Code Coverage Report:**
    ```bash
    ./gradlew koverHtmlReport
    ```
    The report will be available at `app/build/reports/kover/index.html`.
