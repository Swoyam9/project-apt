CREATE DATABASE IF NOT EXISTS auto_spare_parts_coursework
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE auto_spare_parts_coursework;

DROP TABLE IF EXISTS order_details_table;
DROP TABLE IF EXISTS order_table;
DROP TABLE IF EXISTS spare_parts_table;
DROP TABLE IF EXISTS user_table;

CREATE TABLE user_table (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE spare_parts_table (
    part_id INT AUTO_INCREMENT PRIMARY KEY,
    part_name VARCHAR(120) NOT NULL,
    brand VARCHAR(80) NOT NULL,
    category VARCHAR(80) NOT NULL,
    part_number VARCHAR(80) NOT NULL UNIQUE,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    description TEXT
);

CREATE TABLE order_table (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    shipping_address VARCHAR(255) NOT NULL,
    CONSTRAINT fk_order_user
        FOREIGN KEY (user_id) REFERENCES user_table(user_id)
);

CREATE TABLE order_details_table (
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    part_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_details_order
        FOREIGN KEY (order_id) REFERENCES order_table(order_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_details_part
        FOREIGN KEY (part_id) REFERENCES spare_parts_table(part_id)
);

INSERT INTO user_table (full_name, email, phone, address, password, role) VALUES
('System Admin', 'admin@autospares.com', '9800000000', 'Kathmandu', 'admin123', 'ADMIN'),
('Demo Customer', 'customer@example.com', '9811111111', 'Lalitpur', 'customer123', 'USER');

INSERT INTO spare_parts_table
(part_name, brand, category, part_number, price, stock_quantity, description) VALUES
('Premium Oil Filter', 'Bosch', 'Engine Parts', 'BOS-OF-1001', 1500.00, 45, 'Oil filter for petrol engines.'),
('Ceramic Brake Pads', 'Brembo', 'Brake System', 'BRM-BP-2201', 5950.00, 28, 'Front wheel ceramic brake pad set.'),
('12V Car Battery', 'Exide', 'Electrical', 'EXD-BAT-450', 12999.00, 12, 'Reliable 12V battery.'),
('Gas Shock Absorber', 'Monroe', 'Suspension', 'MON-SHOCK-300', 7425.00, 20, 'Rear gas shock absorber.'),
('Side Mirror Assembly', 'Dorman', 'Body Parts', 'DOR-MIR-991', 3875.00, 16, 'Manual side mirror replacement.');

INSERT INTO order_table
(user_id, total_amount, status, shipping_address) VALUES
(2, 7450.00, 'CONFIRMED', 'Lalitpur');

INSERT INTO order_details_table
(order_id, part_id, quantity, unit_price) VALUES
(1, 1, 1, 1500.00),
(1, 2, 1, 5950.00);
