# 🏋️ GYM-MANAGER

A client-server desktop application for tracking and analyzing personal gym training progress. Built entirely in Java, with a JavaFX desktop client and a custom multithreaded socket server backed by a database.

## ✨ Features

- 🔐 User registration and login
- 📋 Create and manage personalized workout routines ("schede")
- 🏋️ Add, edit, and organize exercises within a routine
- ⚖️ Log body weight and training load over time
- 📈 Visualize progress with charts (per exercise and per routine)
- 🗂️ Dashboard overview of all saved routines

## 🏗️ Architecture

A classic **client-server** architecture, communicating over raw Java sockets rather than HTTP/REST:

```
GYM-MANAGER/
├── CLIENT_GYM_MANAGER/   # JavaFX desktop client (FXML-based UI)
└── GYM_SERVER/           # Multithreaded Java socket server
    └── src/server/
        ├── Main.java             # Server entry point
        ├── Server.java           # Socket listener
        ├── ServerThread.java     # Per-client connection handler
        └── DbmsComunication.java # Database access layer
```

- **Client:** Java + JavaFX, UI defined via FXML
- **Server:** custom multithreaded socket server — a dedicated `ServerThread` handles each connected client concurrently
- **Persistence:** dedicated database communication layer (`DbmsComunication`)

## 🛠️ Tech Stack

| Component | Technology |
|---|---|
| Desktop UI | JavaFX (FXML) |
| Backend | Java (multithreaded socket server) |
| Communication | Java Sockets (client ↔ server) |
| Data | Relational database |

## 📦 Getting Started

### Prerequisites
- JDK 17+
- JavaFX SDK (for the client)

### Running it
```bash
git clone https://github.com/Matthew3337/GYM-MANAGER.git
cd GYM-MANAGER

# 1. Start the server
cd GYM_SERVER/src
javac server/*.java
java server.Main

# 2. Run the client
cd ../../CLIENT_GYM_MANAGER
# open and run in your IDE, or compile/run the client entry point
```

## 📄 License

This project is for educational and portfolio purposes.
