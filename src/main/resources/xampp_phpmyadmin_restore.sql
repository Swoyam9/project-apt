CREATE DATABASE IF NOT EXISTS auto_spare_parts_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE auto_spare_parts_db;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    password_hash VARCHAR(64) NOT NULL,
    password_salt VARCHAR(64) NOT NULL,
    role ENUM('USER','ADMIN') NOT NULL DEFAULT 'USER',
    profile_image VARCHAR(255) DEFAULT 'default-profile.png',
    remember_token VARCHAR(128),
    remember_token_expiry DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(80) NOT NULL UNIQUE,
    description VARCHAR(255)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    name VARCHAR(120) NOT NULL,
    brand VARCHAR(80) NOT NULL,
    part_number VARCHAR(80) NOT NULL UNIQUE,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    image_path VARCHAR(255) DEFAULT 'default-product.jpg',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status ENUM('PENDING','CONFIRMED','SHIPPED','DELIVERED','CANCELLED') NOT NULL DEFAULT 'PENDING',
    shipping_address VARCHAR(255) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO categories (name, description) VALUES
('Engine Parts', 'Filters, plugs, belts, pistons and engine maintenance items'),
('Brake System', 'Brake pads, rotors, calipers and brake accessories'),
('Electrical', 'Batteries, bulbs, sensors and wiring components'),
('Suspension', 'Shock absorbers, struts and control arms'),
('Body Parts', 'Mirrors, bumpers, handles and exterior fittings');

INSERT INTO users (full_name, email, phone, address, password_hash, password_salt, role)
VALUES ('System Admin', 'admin@autospares.com', '9800000000', 'Admin Office',
'9f9ac2847ba95b9f4db9813c3fde37ffeb707e72cf2bbf4b585fd5af973d10e5', 'autospares-admin-salt', 'ADMIN');

INSERT INTO products (category_id, name, brand, part_number, description, price, stock_quantity, image_path) VALUES
(1, 'Premium Oil Filter', 'Bosch', 'BOS-OF-1001', 'High-efficiency oil filter for petrol engines.', 1500.00, 45, 'engine-filter.jpg'),
(2, 'Ceramic Brake Pads', 'Brembo', 'BRM-BP-2201', 'Low-dust ceramic brake pad set for front wheels.', 5950.00, 28, 'brake-pads.jpg'),
(3, '12V Car Battery', 'Exide', 'EXD-BAT-450', 'Reliable 12V battery with strong cold start performance.', 12999.00, 12, 'battery.jpg'),
(4, 'Gas Shock Absorber', 'Monroe', 'MON-SHOCK-300', 'Rear gas shock absorber for smooth ride control.', 7425.00, 20, 'shock-absorber.png'),
(5, 'Side Mirror Assembly', 'Dorman', 'DOR-MIR-991', 'Manual side mirror replacement assembly.', 3875.00, 16, 'side-mirror.jpg');
