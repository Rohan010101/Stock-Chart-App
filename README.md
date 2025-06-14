# 📈 Stock Chart App

A beautifully designed and fully functional **Stock Market Viewer** app built using **Kotlin** and **Jetpack Compose**.  
This app allows users to explore stock data, view company-wise sparklines, track up/down trends, and navigate between companies — all powered by clean architecture and modern Android development best practices.


## 🌟 Features

- 🔍 Search and explore companies
- 📊 View sparkline charts showing stock movement
- ⬆️⬇️ Trend indicator (Up/Down) based on stock value changes
- 🔁 Pull-to-refresh for latest data
- 💉 Global state with shared ViewModel across screens
- 🔄 Reactive UI with Kotlin Flow + ViewModel
- 🧱 MVVM Architecture with Hilt and Clean Compose practices
- 🎨 Beautiful Material 3 UI with dark/light theme support

---

## 🛠 Tech Stack

| Layer            | Library / Tool                                                                 |
|------------------|--------------------------------------------------------------------------------|
| 💻 Language       | [Kotlin](https://kotlinlang.org/)                                               |
| 🎨 UI             | [Jetpack Compose](https://developer.android.com/jetpack/compose)               |
| 📊 Charts         | Custom Composables (`Canvas`, `Path`) for Sparkline + [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) *(planned upgrade)* |
| 🔁 State Mgmt     | [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), `StateFlow` |
| 🗺 Navigation      | [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation) |
| 💉 DI             | [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) |
| 🔃 Swipe Refresh   | [Accompanist SwipeRefresh](https://google.github.io/accompanist/swiperefresh/) |
| 🧪 Testing         | *(Coming soon – to be integrated)*                                             |
| 🎨 Theming        | Material 3, Typography, Custom Color Schemes                                   |

---

## 📚 What I Learned

Through this project, I explored:

- ⚙️ Building Jetpack Compose UIs with custom sparkline visualizations
- 🧠 Handling state cleanly via `StateFlow` and `collectAsState()`
- 📲 Navigating between screens with shared `ViewModel` using DI
- 🧩 Integrating Accompanist libraries like `SwipeRefresh`
- 🏗️ Applying MVVM and clean architecture principles practically
- 🎨 Making reusable components and responsive layouts in Compose
- 📊 (Upcoming) Planning to integrate **MPAndroidChart** for more advanced charting capabilities

---

## 🛠 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/stock-chart-app.git
cd stock-chart-app
