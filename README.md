# 🏦 Banking Management System

A console-based **Java Banking Management System** that enables users to securely perform essential banking operations such as creating accounts, depositing, withdrawing money, checking balances, viewing transactions, and closing accounts. All account data and transactions are stored in a **MySQL database** with proper validations and PIN-based authentication.

---

## 📌 Project Highlights

- Developed in Java (Core Java + JDBC)
- Interactive menu-driven console UI
- Secure PIN-based authentication
- Tracks and logs transactions
- Fully integrated with **MySQL**
- Follows clean Object-Oriented design

---

## 📁 Project Structure

Banking Management System/
├── src/
│ └── Banking/
│ ├── Main.java
│ └── BankingService.java
├── lib/
│ └── mysql-connector-j-9.0.0.jar
├── DBConnection.java (You will add this)
├── README.md
└── .gitignore


---

## 🎯 Features

- ✅ Create a new bank account
- 💰 Deposit money
- 💸 Withdraw money with balance check
- 👁️ View current balance
- 🧾 View all transactions (latest first)
- 📃 View full account details
- 🗑️ Close account securely
- 🔐 PIN-based security

---

## ⚙️ Technologies Used

- Java 11+ (Core Java, JDBC)
- MySQL 8.x
- Eclipse IDE
- MySQL Connector/J
- Git & GitHub

---

## 🧱 Database Details

### 📂 Database: `banking_system`

#### 🔹 Table: `accounts`

| Field     | Type          | Description               |
|-----------|---------------|---------------------------|
| id        | INT, PK, AI   | Account ID                |
| name      | VARCHAR(100)  | Account Holder Name       |
| pin       | INT           | 4-digit PIN               |
| balance   | DOUBLE        | Current Balance           |
| status    | VARCHAR(20)   | `ACTIVE` or `CLOSED`      |

#### 🔹 Table: `transactions`

| Field       | Type          | Description             |
|-------------|---------------|-------------------------|
| id          | INT, PK, AI   | Transaction ID          |
| account_id  | INT (FK)      | Linked Account ID       |
| type        | VARCHAR(20)   | `DEPOSIT`, `WITHDRAW`, `CLOSE` |
| amount      | DOUBLE        | Transaction Amount      |
| timestamp   | TIMESTAMP     | Date & Time             |
