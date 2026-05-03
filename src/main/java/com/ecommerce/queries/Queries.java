package com.ecommerce.queries;

public class Queries {
    
    // User Queries
    public static final String CHECK_USER_LOGIN = 
        "SELECT user_id, email, password, role, active FROM users WHERE email = ?";
    
    public static final String GET_USER_BY_EMAIL = 
        "SELECT * FROM users WHERE email = ?";
    
    public static final String REGISTER_USER = 
        "INSERT INTO users (first_name, last_name, email, password, phone, address, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    public static final String CHECK_EMAIL_EXISTS = 
        "SELECT 1 FROM users WHERE email = ?";
    
    public static final String GET_ALL_USERS = 
        "SELECT * FROM users ORDER BY created_date DESC";
    
    public static final String UPDATE_USER_STATUS = 
        "UPDATE users SET active = ? WHERE user_id = ?";
    
    // Product Queries
    public static final String GET_ALL_PRODUCTS = 
        "SELECT p.*, c.name as category_name FROM products p LEFT JOIN categories c ON p.category_id = c.category_id ORDER BY p.created_date DESC";
    
    public static final String GET_PRODUCT_BY_ID = 
        "SELECT p.*, c.name as category_name FROM products p LEFT JOIN categories c ON p.category_id = c.category_id WHERE p.product_id = ?";
    
    public static final String ADD_PRODUCT = 
        "INSERT INTO products (name, description, price, stock_quantity, category_id, manufacturer, model_compatibility, image_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    public static final String UPDATE_PRODUCT = 
        "UPDATE products SET name=?, description=?, price=?, stock_quantity=?, category_id=?, manufacturer=?, model_compatibility=?, image_url=? WHERE product_id=?";
    
    public static final String DELETE_PRODUCT = 
        "DELETE FROM products WHERE product_id = ?";
    
    public static final String SEARCH_PRODUCTS = 
        "SELECT p.*, c.name as category_name FROM products p LEFT JOIN categories c ON p.category_id = c.category_id WHERE LOWER(p.name) LIKE ? OR LOWER(p.description) LIKE ? OR LOWER(p.manufacturer) LIKE ?";
    
    public static final String GET_PRODUCTS_BY_CATEGORY = 
        "SELECT p.*, c.name as category_name FROM products p LEFT JOIN categories c ON p.category_id = c.category_id WHERE p.category_id = ?";
    
    public static final String UPDATE_PRODUCT_STOCK = 
        "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ? AND stock_quantity >= ?";
    
    // Order Queries
    public static final String CREATE_ORDER = 
        "INSERT INTO orders (user_id, total_amount, shipping_address, payment_method, status) VALUES (?, ?, ?, ?, ?)";
    
    public static final String ADD_ORDER_ITEM = 
        "INSERT INTO order_items (order_id, product_id, quantity, price_at_time) VALUES (?, ?, ?, ?)";
    
    public static final String GET_USER_ORDERS = 
        "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
    
    public static final String GET_ALL_ORDERS = 
        "SELECT o.*, CONCAT(u.first_name, ' ', u.last_name) as user_name FROM orders o JOIN users u ON o.user_id = u.user_id ORDER BY o.order_date DESC";
    
    public static final String GET_ORDER_DETAILS = 
        "SELECT * FROM orders WHERE order_id = ?";
    
    public static final String GET_ORDER_ITEMS = 
        "SELECT oi.*, p.name as product_name FROM order_items oi JOIN products p ON oi.product_id = p.product_id WHERE oi.order_id = ?";
    
    public static final String UPDATE_ORDER_STATUS = 
        "UPDATE orders SET status = ? WHERE order_id = ?";
    
    // Category Queries
    public static final String GET_ALL_CATEGORIES = 
        "SELECT * FROM categories";
    
    public static final String GET_CATEGORY_BY_ID = 
        "SELECT * FROM categories WHERE category_id = ?";
    
    // Dashboard Queries
    public static final String GET_TOTAL_PRODUCTS = 
        "SELECT COUNT(*) as total FROM products";
    
    public static final String GET_TOTAL_USERS = 
        "SELECT COUNT(*) as total FROM users";
    
    public static final String GET_TOTAL_ORDERS = 
        "SELECT COUNT(*) as total FROM orders";
    
    public static final String GET_TOTAL_REVENUE = 
        "SELECT SUM(total_amount) as total FROM orders WHERE status = 'delivered'";
    
    public static final String GET_RECENT_ORDERS = 
        "SELECT o.*, CONCAT(u.first_name, ' ', u.last_name) as user_name FROM orders o JOIN users u ON o.user_id = u.user_id ORDER BY o.order_date DESC LIMIT 5";
}