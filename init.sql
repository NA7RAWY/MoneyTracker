-- Create the database
CREATE DATABASE IF NOT EXISTS moneytracker_db;

-- Create the user and grant privileges
CREATE USER IF NOT EXISTS 'me'@'%' IDENTIFIED BY '236236';
GRANT ALL PRIVILEGES ON moneytracker_db.* TO 'me'@'%';

-- Create tables (optional, as Hibernate will handle this with ddl-auto=update)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    job_type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    transaction_date DATE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    is_income BOOLEAN NOT NULL,
    category VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insert test data into users table
INSERT INTO users (first_name, last_name, email, password, job_type) VALUES
('John', 'Doe', 'john.doe@example.com', 'password123', 'Software Engineer'),
('Jane', 'Smith', 'jane.smith@example.com', 'securepass456', 'Teacher'),
('Alice', 'Johnson', 'alice.johnson@example.com', 'mypassword789', 'Accountant');

-- Insert test data into transactions table
INSERT INTO transactions (user_id, transaction_date, amount, is_income, category) VALUES
(1, '2025-01-05', 3000.00, true, 'Salary'),
(1, '2025-01-10', 150.50, false, 'Groceries'),
(1, '2025-01-15', 75.25, false, 'Utilities'),
(1, '2025-01-20', 200.00, false, 'Entertainment'),
(2, '2025-01-07', 2500.00, true, 'Salary'),
(2, '2025-01-12', 80.00, false, 'Transportation'),
(2, '2025-01-18', 120.75, false, 'Groceries'),
(3, '2025-01-03', 4000.00, true, 'Freelance'),
(3, '2025-01-09', 300.00, false, 'Rent'),
(3, '2025-01-25', 50.00, false, 'Dining');