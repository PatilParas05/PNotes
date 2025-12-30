# PNote: Your Thoughts, Organized

<img src="https://raw.githubusercontent.com/PatilParas05/PNotes/main/Screenshot/logo.png" alt="PNote Logo" height="50" width="50"/>A simple yet effective Personal Note (PNote) application built with **Kotlin** and **Jetpack Compose**, designed to help you quickly jot down and manage your thoughts. All your notes are securely stored locally using **SQLite** (powered by the Room Persistence Library), ensuring your data is always at your fingertips.

## ✨ Features

* **Create New Notes:** Easily add new notes with a title and content.
* **View All Notes:** See a list of all your notes, sorted by the last modified date.
* **Edit Existing Notes:** Tap on any note to update its title or content.
* **Delete Notes:** Remove notes you no longer need.
* **Automatic Timestamping:** Each note automatically tracks its creation and last modified date.
* **Intuitive UI:** A clean and beginner-friendly user interface built with Jetpack Compose.
* **Local Data Storage:** All data is stored securely on your device using Room Database (SQLite).

## 🚀 Technologies Used

* **Kotlin:** The primary programming language.
* **Jetpack Compose:** Modern Android UI toolkit for building native UIs.
* **Jetpack Room Persistence Library:** ORM for SQLite database interaction, making data storage seamless.
* **Jetpack ViewModel:** To manage UI-related data in a lifecycle-conscious way.
* **Jetpack Navigation Compose:** For managing in-app navigation between screens.

## 🛠️ Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

* Android Studio Arctic Fox (or newer)
* Android SDK (API Level 26 or higher recommended for `minSdk` and `targetSdk`)
* A physical Android device or an Android Emulator

### Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/PatilParas05/PNotes.git
    ```
2.  **Open the project in Android Studio:**
    * Launch Android Studio.
    * Click on `Open` an existing Android Studio project.
    * Navigate to the directory where you cloned the repository and select the project.
3.  **Sync Gradle:**
    * Let Android Studio sync the Gradle files. If it doesn't do so automatically, click `File` > `Sync Project with Gradle Files`.
4.  **Run on Device/Emulator:**
    * Select your desired emulator or connected physical device from the toolbar.
    * Click the `Run` button (green triangle) to build and install the app.

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.
(If you don't have a LICENSE file, you can create one. MIT is a common choice for open-source projects.)

---
