# AI-based Trading Simulator

The **AI-based Trading Simulator** is a fully-fledged platform designed for crypto trading enthusiasts and professionals. By integrating real-time data, AI-driven recommendations, and portfolio management tools, this simulator enables users to learn trading strategies, track their portfolio performance, and gain insights into market trends without any real financial risk.

This project is built with **Spring Boot** for the backend, **React** for the frontend, and utilizes advanced AI to help simulate trades based on real-time market data.

## 📋 Project Overview

The **AI-based Trading Simulator** provides a safe and interactive environment for users to practice cryptocurrency trading. The platform allows users to simulate buying, selling, and transferring crypto assets with real-time data provided by **Gemini** and **CoinGecko APIs**. The system also includes an AI-powered mentor to provide personalized trading recommendations and strategies.

This project is developed by **Seema** and is aimed at individuals who want to get familiar with cryptocurrency trading or enhance their trading skills.

## 🌟 Features

- **Real-Time Crypto Data**: Access live market data for over 1000 cryptocurrencies using the Gemini API and CoinGecko API.
- **AI-Powered Trading Mentor**: Receive personalized, AI-driven recommendations based on your trading behavior, experience level, and risk tolerance.
- **Simulated Crypto Transactions**: Simulate buying, selling, and transferring cryptocurrencies without the risk of real money.
- **Portfolio Management**: Track your simulated crypto portfolio, monitor performance, and manage your assets.
- **Wallet Management**: Securely transfer assets between wallets, add balances, and withdraw funds to simulated accounts.
- **Advanced Security Features**: Ensure the safety of user data and simulated transactions with JWT-based authentication and two-factor authentication.
- **Trading History**: View and analyze your past trades, portfolio performance, and account balances.

## 🛠️ Technologies Used

### Back-End:
- **Spring Boot** (Java)
- **MySQL** (Database)
- **Spring Security** (Authentication)
- **JWT** (JSON Web Tokens for user authentication)
- **Gemini API** (Real-time data)
- **CoinGecko API** (Market data)

### AI:
- **Google Gemini API** (AI-driven trading recommendations)

### Payment Gateways (Simulated):
- **Razorpay** (For payment simulation)

## 🧑‍💻 Project Roles

-*Seema Dwivedi: Back-end development ,AI integration, security features and database management.*

## 📥 Installation and Setup

To set up this project locally, follow these steps:

### Prerequisites

Follow these steps to set up the AI-based Trading Simulator on your local machine:

- **Java 17 or higher** (For backend development)
- **Node.js** and **npm** (For frontend development)
- **MySQL** (For database management)
- **Git** (For cloning the repository)

### Step 1: Clone the Repository

First, clone the repository to your local machine:
```bash
git clone https://github.com/seema1410/AI-Trading-Simulator.git
cd AI-Trading-Simulator
```
### Step 2: Install Backend Dependencies

Navigate to the backend directory and install required dependencies:
```bash
cd backend
mvn clean install
```
### Step 3: Configure Database

Update the database credentials in the `.env` file (or application.properties in Spring Boot):
```
DB_URL=jdbc:mysql://localhost:3306/your_database_name
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
```

### Step 4: Run the Backend
```bash
cd backend
mvn spring-boot:run
```
### Step 5: Access the Application

Open your browser and go to:

Backend: `http://localhost:8000`

##  Usage

Once the application is running, you can log in and start simulating trades. Create a new user or log in to an existing account. The platform will guide you through setting up a virtual wallet, portfolio, and accessing real-time crypto market data.

- **Simulate Crypto Transactions**: Buy, sell, and transfer crypto assets without the risk of real money.
- **AI-Powered Trading Mentor**: Receive personalized trading recommendations.
- **Portfolio & Transaction Management**: Track your simulated portfolio and review your transaction history.

##  Contact Information

For any questions or support related to the AI-based Trading Simulator project, feel free to reach out:

- **Seema** (Developer)
