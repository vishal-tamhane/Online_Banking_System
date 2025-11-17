# 🏦 Online Banking Management System (Java + PostgreSQL)

A complete **console-based banking system** built using **Java, JDBC, PostgreSQL, BCrypt hashing, OOP, DAO-Service architecture**, and multi-role user access.

This project is designed to simulate real-world banking backend operations, including:

- Customer account management  
- Admin approval workflows  
- Loan & credit card processing  
- Secure authentication  
- Transaction logging  

---

## 🚀 Tech Stack

| Layer | Technology |
|------|------------|
| Language | **Java 23** |
| Database | **PostgreSQL** |
| Architecture | **DAO + Service + Model + Menu** |
| Security | **BCrypt hashing (PIN encryption)** |
| Logging | **Custom audit logs stored in DB** |
| Build Tool | **Maven** |

---

## 📌 Features

### 👤 Customer Features

- **Create New Account**  
- **Secure Login (Phone + Aadhaar + PIN)**  
- **BCrypt encrypted PIN storage**  
- **Deposit Money**  
- **Withdraw Money** (₹500 minimum balance)  
- **Check Balance**  
- **Send Money / Fund Transfer**
  - Same bank transfer  
  - Other bank transfer (2% charge)  
- **View Transaction History**  
- **Apply for Loans**
  - Auto-reject if credit score < 100  
  - Routes to A1/A2/A3 admin based on amount  
- **View Loan Status**
- **Apply for Credit Card**
  - Eligible when credit score ≥ 400  
  - Limit = 3 × monthly salary  

---

## 🧑‍💼 Admin Features

Admins are divided into four roles:

### 🔸 **A1 – High-Value Loan Admin**  
Approves loans **> ₹1,00,000**

### 🔸 **A2 – Mid-Value Loan Admin**  
Approves loans **₹20,000 – ₹1,00,000**

### 🔸 **A3 – Low-Value Loan Admin**  
Approves loans **< ₹20,000**

### 🔸 **A4 – Credit Card Manager**  
Approves or rejects all credit card applications

### Admin Capabilities:

- Login using Admin ID & PIN  
- View pending loans  
- Approve / Reject loans  
- Approve / Reject credit cards  
- Auto-generate credit card number, CVV, expiry date  
- View system logs  

---

## 🗄 Database Schema (PostgreSQL)

This project uses 7 main tables:

- **customers**
- **transactions**
- **loans**
- **credit_cards**
- **bills**
- **admins**
- **logs**

Indexes, constraints, and foreign keys improve performance and data integrity.

A full SQL schema is available inside the project.

---

## 🏗 Project Structure

src/
└── com/bankingsystem/
├── main/ # Application entry point
├── database/ # DB Connection (PostgreSQL)
├── models/ # Customer, Admin, Loan, etc.
├── dao/ # Data Access Layer
├── services/ # Business Logic
├── menus/ # Console menus
├── utils/ # Hashing, validation
└── exceptions/ # Custom exceptions



---

## 🔐 Security

### ✔ BCrypt PIN Hashing  
Used for encrypting all customer and admin PINs:

- `BCrypt.hashpw(pin, BCrypt.gensalt())`
- `BCrypt.checkpw(enteredPin, storedHash)`

### ✔ No plain-text passwords stored  
### ✔ Secure authentication flow  
### ✔ Industry-level hashing mechanism  

---

## 📝 Logging System

Every important action is saved into the `logs` table:

- Customer login  
- Money transfers  
- Deposits / withdrawals  
- Loan approval / rejection  
- Credit card approval  
- Admin actions  

Admins can view logs from their menu.

---

## 🧪 How to Run


### 1️⃣ Clone the repository:
```bash
2️⃣ Set up the database in PostgreSQL

Import the provided .sql schema.


Configure DB credentials

Edit DBConnection.java:

private static final String URL = "jdbc:postgresql://localhost:5432/bankingdb";
private static final String USER = "postgres";
private static final String PASS = "yourpassword";

Run the application

Using IntelliJ IDEA or:

mvn clean install
java -jar target/Online_Banking_System.jar

🎯 Learning Outcomes

Through this project, you will learn:

Java OOP principles

Multi-layer backend architecture

JDBC + PostgreSQL operations

BCrypt hashing (security)

Audit logging

Real-world banking workflows

DAO & Service patterns

Error handling

Clean console UI design

