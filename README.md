# ACE Incoporated: Budget tracker app
## Repo link: https://github.com/Thamsanqa123/ACE-Incorporated
## demostration video: https://youtu.be/OkRit3oFyoQ?si=mM6hxAdka3PMLCKI

##  Overview

ACEinc is a mobile expense tracking application built using **Kotlin** and **SQLite** in Android Studio.
The app allows users to register, log in, track their spending, and manage expenses with a clean, modern UI.

The goal of the app is to provide users with a simple but powerful way to monitor their financial activity.

---

##  Features

###  User Authentication

* User registration with:

  * Username
  * Email
  * Password
* Login system with validation
* User-specific data (each user sees only their own expenses)

---

###  Expense Management

Users can:

* Add expenses with:

  * Title
  * Amount
  * Category
  * Date
  * Start & End Time
  * Receipt image upload
* View all saved expenses
* Delete expenses
* Edit functionality (structure implemented, UI expansion pending)

---

###  Dashboard

* Displays:

  * Total balance (calculated from expenses)
* Navigation to:

  * Add Expense
  * View Expenses
  * Account Page

---

###  Account Page

* Displays logged-in user details:

  * Username
  * Email
* Logout functionality

---

###  Expense Display (RecyclerView)

* Modern card-based UI
* Each expense shows:

  * Amount
  * Title
  * Category
  * Date
* Includes:

  * Delete button (with confirmation dialog)
  * Edit button (prepared for future implementation)

---

##  Technologies Used

* **Kotlin** – Main programming language
* **SQLite** – Local database storage
* **Android SDK** – App framework
* **RecyclerView** – Dynamic list display
* **XML** – UI design
* **Material Design Components** – UI styling

---

##  Database Structure

### Users Table

| Column   | Type         |
| -------- | ------------ |
| id       | INTEGER (PK) |
| username | TEXT         |
| email    | TEXT         |
| password | TEXT         |

---

### Expenses Table

| Column    | Type         |
| --------- | ------------ |
| id        | INTEGER (PK) |
| userId    | INTEGER (FK) |
| title     | TEXT         |
| amount    | REAL         |
| category  | TEXT         |
| date      | TEXT         |
| startTime | TEXT         |
| endTime   | TEXT         |
| imageUri  | TEXT         |

---

##  App Flow

1. User signs up
2. User logs in
3. User is redirected to Dashboard
4. User can:

   * Add expenses
   * View expenses
   * Manage account
5. Data is stored locally using SQLite

---

##  UI Design

The app uses a **dark-themed, glassmorphism-inspired UI**, featuring:

* Soft purple highlights
* Transparent card elements
* Clean spacing and typography
* Background logo watermark

---

##  Known Limitations

* Data is stored locally (no cloud sync)
* No password encryption (basic implementation)
* Edit expense screen not fully implemented yet
* No analytics or charts yet

---

## Future Improvements

* Monthly budget goals
* Expense analytics & charts
* Cloud database integration (Firebase)
* User profile customization
* Notifications for spending habits

---

##  Installation

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or physical device

---

##  Author

Developed by **Saint**
Final Year IT Software Development Student

---

##  License

This project is for educational purposes.
