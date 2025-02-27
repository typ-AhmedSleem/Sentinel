# Sentinel - AI-Powered Finance Spam Detection

## 🚀 Introduction
Sentinel is a powerful mobile application designed to **protect users from financial fraud** by detecting and categorizing suspicious messages in real time. The app leverages **Google’s AI & NLU technologies**, including **Gemini API**, to analyze message content and provide users with insights, warnings, and actionable advice.

## 🎯 Key Features
- **🔔 Notification Interceptor System** – Listens to incoming notifications and extracts message content.
- **🛡️ AI Spam Detection** – Uses Google NLU to classify messages as **Safe, Spam, Scam, Phishing, or Fake Loan**.
- **🤖 AI ChatBot (Gemini)** – Provides human-like explanations, warnings, and advice.
- **📊 Risk Analysis Engine** – Determines risk levels with confidence scores.
- **🔒 Secure History System** – Stores past messages securely for review.
- **⚙️ User Control System** – Allows customization of filters and alerts.
- **📩 Report To Authorities System** – Enables reporting suspicious messages to relevant agencies.

## 📌 How It Works
1. The app **listens to incoming notifications** (SMS, banking alerts, loan offers, etc.).
2. Extracted message content is analyzed by **Google NLU & AI models**.
3. Messages are classified as **Safe, Spam, Scam, Phishing, or Fake Loan**.
4. If a threat is detected, **Sentinel warns the user** and suggests the best course of action.
5. Users can **chat with the AI bot (Gemini)** to get explanations and additional insights.

## 🔧 Tech Stack
- **Kotlin Multiplatform (KMP)** – Cross-platform app development.
- **Compose Multiplatform** – Shared UI for Android & iOS.
- **Google NLU APIs** – For message classification.
- **Google Gemini API** – AI-powered chatbot for insights and assistance.
- **Room Database** – Local storage for classified messages.
- **Notification Listener Service** – To capture and analyze incoming messages.

## 🏗️ Project Architecture
Sentinel follows **Clean Architecture** principles:
- **Presentation Layer:** Compose UI, ViewModels
- **Domain Layer:** Use Cases, Business Logic
- **Data Layer:** Repositories, Database (Room), Network API Calls (Google NLU & Gemini)

## 📦 Package Structure
```
com.typ.sentinel
│── core
│   ├── di                 # Dependency Injection (Hilt/Koin)
│   ├── utils              # Utility classes & helpers
│   ├── models             # Data models (sealed classes, DTOs, etc.)
│   ├── common             # Common reusable logic
│
│── data
│   ├── repository         # Repository implementations
│   ├── datasource
│   │   ├── local         # Room Database, Preferences
│   │   ├── remote        # Network (if needed, Firebase, APIs)
│   │   ├── system        # OS-level access (notifications, storage)
│   ├── mappers           # Converters (DB models ⇄ Domain models)
│
│── domain
│   ├── models            # Business logic models (clean architecture)
│   ├── usecase           # Interactors (business logic operations)
│   ├── repository        # Repository interfaces (for abstraction)
│
│── presentation
│   ├── ui
│   │   ├── components    # Reusable UI components
│   │   ├── screens       # UI Screens (Home, Settings, Message Details)
│   ├── viewmodel         # ViewModels (MVVM)
│   ├── navigation        # Navigation & routes
│
│── features
│   ├── notification      # Notification Interceptor System
│   ├── spamdetection     # Spam Detection System (AI integration)
│   ├── ai                # AI Engine (Google Gemini API)
│   ├── riskanalysis      # Risk Analysis Engine
│   ├── history           # Secure History System (local storage)
│   ├── usercontrol       # User Control System (Actions, settings)
│   ├── reporting         # Report To Authorities System
│
│── platform              # Platform-specific implementations
│   ├── android
│   │   ├── services      # Foreground services (Notification listener)
│   │   ├── permissions   # System permissions handling
│   ├── ios
│       ├── services      # iOS-specific services (Notification listener)
│       ├── permissions   # System permissions handling
```

## 🚀 Getting Started
### 1️⃣ Clone the Repository
```bash
git clone https://github.com/typ-AhmedSleem/sentinel.git
cd sentinel
```
### 2️⃣ Set Up API Keys
- Get API keys for **Google Gemini** and **Google NLU APIs**.
- Store them securely in **local.properties**.

### 3️⃣ Build and Run
```bash
gradlew build
```

## 📢 Contributing
We welcome contributions! Feel free to open issues and PRs.

## 📜 License
This project is licensed under the **MIT License**.

## 📬 Contact
For inquiries, reach out at **typahmedsleem@gmail.com**

---

### ⚡ Let's build a safer finance experience with AI! 🚀

