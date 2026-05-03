package com.ecommerce.dao;

import com.ecommerce.models.CartItem;
import com.ecommerce.models.Order;
import com.ecommerce.models.OrderItem;
import com.ecommerce.queries.Queries;
import com.ecommerce.utils.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public int createOrder(int userId, String shippingAddress, List<CartItem> cartItems) throws SQLException {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new SQLException("Cart is empty");
        }

        BigDecimal total = cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement orderPs = con.prepareStatement(Queries.CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
                orderPs.setInt(1, userId);
                orderPs.setBigDecimal(2, total);
                orderPs.setString(3, shippingAddress);
                orderPs.executeUpdate();

                int orderId;
                try (ResultSet keys = orderPs.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("Could not create order");
                    }
                    orderId = keys.getInt(1);
                }

                for (CartItem item : cartItems) {
                    reduceStock(con, item);
                    insertOrderItem(con, orderId, item);
                }

                con.commit();
                return orderId;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public List<Order> findByUser(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.LIST_ORDERS_BY_USER)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setTotalAmount(rs.getBigDecimal("total_amount"));
                    order.setStatus(rs.getString("status"));
                    order.setShippingAddress(rs.getString("shipping_address"));
                    order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                    order.setItems(findItems(con, order.getId()));
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    private void reduceStock(Connection con, CartItem item) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(Queries.REDUCE_STOCK)) {
            ps.setInt(1, item.getQuantity());
            ps.setInt(2, item.getProduct().getId());
            ps.setInt(3, item.getQuantity());
            if (ps.executeUpdate() != 1) {
                throw new SQLException("Insufficient stock for " + item.getProduct().getName());
            }
        }
    }

    private void insertOrderItem(Connection con, int orderId, CartItem item) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(Queries.INSERT_ORDER_ITEM)) {
            ps.setInt(1, orderId);
            ps.setInt(2, item.getProduct().getId());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getProduct().getPrice());
            ps.executeUpdate();
        }
    }

    private List<OrderItem> findItems(Connection con, int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Queries.LIST_ORDER_ITEMS)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setProductName(rs.getString("product_name"));
                    item.setPartNumber(rs.getString("part_number"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    items.add(item);
                }
            }
        }
        return items;
    }
}
