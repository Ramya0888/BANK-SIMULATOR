

CREATE DATABASE IF NOT EXISTS bankdb;
USE bankdb;

CREATE TABLE IF NOT EXISTS customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    aadhar_number VARCHAR(20),
    permanent_address VARCHAR(255),
    state VARCHAR(50),
    country VARCHAR(50),
    city VARCHAR(50),
    email VARCHAR(100),
    phone_number VARCHAR(20),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    dob DATE,
    age INT,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    gender VARCHAR(10),
    father_name VARCHAR(100),
    mother_name VARCHAR(100)
);
CREATE TABLE IF NOT EXISTS accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    account_type VARCHAR(30),
    bank_name VARCHAR(100),
    branch VARCHAR(100),
    balance DECIMAL(15,2) DEFAULT 0,
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    account_number VARCHAR(50) UNIQUE,
    ifsc_code VARCHAR(20),
    name_on_account VARCHAR(100),
    phone_linked VARCHAR(20),
    saving_amount DECIMAL(15,2) DEFAULT 0,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transaction_amount DECIMAL(18,2) NOT NULL,

    -- Sender details
    sender_account_id INT NULL,
    sender_account_number VARCHAR(30) NULL,

    -- Receiver details
    receiver_account_id INT NULL,
    receiver_account_number VARCHAR(30) NULL,

    balance_amount DECIMAL(18,2) NOT NULL,
    description VARCHAR(255),
    transaction_type VARCHAR(20) NOT NULL,          -- DEBIT / CREDIT / TRANSFER
    mode_of_transaction VARCHAR(50),               -- UPI / NEFT / IMPS / CASH

    CONSTRAINT fk_sender FOREIGN KEY (sender_account_id) REFERENCES accounts(account_id),
    CONSTRAINT fk_receiver FOREIGN KEY (receiver_account_id) REFERENCES accounts(account_id)
);

