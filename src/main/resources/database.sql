-- Create Database
CREATE DATABASE IF NOT EXISTS auto_parts_ecommerce;
USE auto_parts_ecommerce;

-- Users Table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    role ENUM('admin', 'user') DEFAULT 'user',
    active ENUM('active', 'banned') DEFAULT 'active',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Categories Table
CREATE TABLE categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    price DECIMAL(10,2) DEFAULT 0.00
);

-- Products Table
CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    category_id INT,
    image_url VARCHAR(255),
    manufacturer VARCHAR(100),
    model_compatibility VARCHAR(200),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- Orders Table
CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    status ENUM('pending', 'confirmed', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
    shipping_address TEXT,
    payment_method VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Order Items Table
CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price_at_time DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Insert Categories
INSERT INTO categories (name, description, price) VALUES
('Engine Parts', 'Engine components and spare parts', 0),
('Brake System', 'Brake pads, rotors, and calipers', 0),
('Electrical System', 'Batteries, alternators, and wiring', 0),
('Suspension', 'Shocks, struts, and control arms', 0),
('Transmission', 'Gearboxes and transmission parts', 0),
('Exhaust System', 'Mufflers and exhaust pipes', 0),
('Cooling System', 'Radiators and cooling fans', 0),
('Lighting', 'Headlights and taillights', 0);

-- Insert Admin User (password: admin123)
INSERT INTO users (first_name, last_name, email, password, role) VALUES
('Admin', 'User', 'admin@autoparts.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewKyYr.pYyVK9rMq', 'admin');

-- Insert Sample Products
INSERT INTO products (name, description, price, stock_quantity, category_id, manufacturer, model_compatibility) VALUES
('Premium Brake Pad Set', 'Ceramic brake pads for superior stopping power', 45.99, 100, 2, 'Bosch', 'Universal Fit'),
('Oil Filter', 'High efficiency oil filter for engine protection', 12.99, 200, 1, 'Fram', 'Most Vehicles'),
('Car Battery', '12V maintenance-free battery', 129.99, 50, 3, 'Exide', 'Universal'),
('Spark Plug Set', 'Iridium spark plugs for better performance', 24.99, 150, 1, 'NGK', 'Most Vehicles'),
('Brake Rotor', 'Premium drilled brake rotor', 89.99, 75, 2, 'Brembo', 'Universal'),
('Alternator', 'High output alternator', 199.99, 30, 3, 'Denso', 'Multiple Models'),
('Shock Absorber', 'Heavy duty shock absorber', 119.99, 60, 4, 'Bilstein', 'SUV/Truck'),
('Clutch Kit', 'Complete clutch replacement kit', 299.99, 25, 5, 'Exedy', 'Various Models'),
('Radiator', 'Aluminum racing radiator', 179.99, 40, 7, 'Mishimoto', 'Universal'),
('LED Headlight Kit', 'Bright LED headlight conversion', 79.99, 100, 8, 'Philips', 'Universal');