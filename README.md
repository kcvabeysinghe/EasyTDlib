# EasyTDLib 🚀

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![TDLib](https://img.shields.io/badge/TDLib-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

A **Kotlin-first, ready-to-use boilerplate** for building Android Telegram clients using TDLib — no C++, no CMake, no NDK headaches.

Configuring TDLib for Android is notoriously painful. **EasyTDLib** skips all of that by providing a perfectly pre-configured Android Studio project with working JNI bindings, a complete authentication flow, and a functional chat list — so you can focus entirely on building your app's UI and features.

> **Note on GitHub Language Stats:** GitHub reports this repo as mostly Java due to `TdApi.java` — TDLib's auto-generated API bindings (100,000+ lines). All application code is written in pure Kotlin.

---

## ✨ What's Already Done For You

- ✅ TDLib linked and configured (no NDK/CMake setup needed)
- ✅ Pre-compiled `.so` binaries included and ready
- ✅ Full authentication flow — Phone → OTP → 2FA → Ready
- ✅ Chat list loading with avatar image downloading
- ✅ Logout and TDLib client reboot handled automatically
- ✅ Clean `StateFlow`-based architecture (Kotlin Coroutines)
- ✅ Built with Jetpack Compose (Material 3, dark theme)

---

## 🛠️ Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- Android SDK 26+
- A Telegram account

### Step 1 — Clone the Repository

```bash
git clone https://github.com/kcvabeysinghe/EasyTDlib.git
cd EasyTDlib
```

### Step 2 — Get Your Telegram API Credentials

1. Go to [my.telegram.org](https://my.telegram.org) and log in
2. Click **API Development Tools**
3. Create a new application — you will receive an `api_id` and `api_hash`

### Step 3 — Add Your API Credentials

Open `app/src/main/java/com/example/easytdlib/engine/TelegramManager.kt` and replace the placeholders:

```kotlin
private val apiId   = 0                 // Replace with your api_id
private val apiHash = "YOUR_API_HASH"   // Replace with your api_hash
```

> ⚠️ **Never commit your real API credentials to a public repository.**
> For production apps, load them from `local.properties` via `BuildConfig`.

### Step 4 — Sync and Run

Open the project in Android Studio, let Gradle sync, then hit **Run**. That's it.

---

## 📁 Project Structure

```
app/src/main/
├── java/com/example/easytdlib/
│   ├── engine/
│   │   └── TelegramManager.kt      # Core TDLib engine — auth, chat loading, file downloads
│   ├── ui/
│   │   ├── components/
│   │   │   └── ChatListItem.kt     # Single chat row with avatar
│   │   └── screens/
│   │       └── ChatListScreen.kt   # Chat list with logout
│   └── MainActivity.kt             # Auth router (phone → code → 2FA → chat list)
└── jniLibs/
    ├── arm64-v8a/                  # Pre-compiled TDLib .so binaries
    ├── armeabi-v7a/
    ├── x86/
    └── x86_64/
```

---

## 🧑‍💻 What You Build Next

EasyTDLib handles the hard part. Here's what's left for you:

- 💬 Individual chat / message screen
- ✉️ Sending messages
- 🖼️ Media handling (photos, videos, files)
- 🔔 Push notifications
- 🎨 Your own custom UI & theme
- ⚙️ Settings screen

---

## 🔄 Rename the Package

This template uses `com.example.easytdlib` as a universally recognized placeholder that is also blocked by Google Play — which forces you to choose a unique package name before publishing. To rename it:

1. In Android Studio, select the `app` folder
2. Press `Ctrl + Shift + R` (Replace in Files)
3. Find: `com.example.easytdlib`
4. Replace with: `com.yourdomain.yourappname`
5. Click **Replace All** → Sync Gradle

---

## 📚 Dependencies

| Library | Purpose |
|---|---|
| [TDLib](https://github.com/tdlib/td) | Telegram Database Library |
| [Jetpack Compose](https://developer.android.com/compose) | UI framework |
| [Coil](https://coil-kt.github.io/coil/) | Avatar image loading |
| [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) | Async & StateFlow |

---

## 🤝 Credits & Acknowledgements

- Pre-compiled TDLib binaries sourced from [TGX-Android/tdlib](https://github.com/TGX-Android/tdlib) — licensed under **BSL-1.0**
- [TDLib](https://github.com/tdlib/td) by Telegram — licensed under **BSL-1.0**

The full BSL-1.0 license text is included in this repository under [`THIRD_PARTY_LICENSES.txt`](THIRD_PARTY_LICENSES.txt).

---

## 📜 License

This project is open-source and available under the **MIT License** — see the [LICENSE](LICENSE) file for details.

The pre-compiled TDLib binaries included in `jniLibs/` are sourced from [TGX-Android/tdlib](https://github.com/TGX-Android/tdlib) and are licensed separately under the **Boost Software License 1.0 (BSL-1.0)**.

---

## ⭐ Support

If this saved you hours of TDLib configuration pain, consider leaving a ⭐ on the repo — it helps other developers find it!

Found a bug or have a suggestion? Open an [Issue](https://github.com/kcvabeysinghe/EasyTDlib/issues).