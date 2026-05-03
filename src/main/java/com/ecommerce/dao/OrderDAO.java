package com.ecommerce.dao;

import com.ecommerce.models.OrderModel;
import com.ecommerce.models.OrderItemModel;
import com.ecommerce.utils.DatabaseConn;
import com.ecommerce.queries.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    
    public int createOrder(OrderModel order) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getShippingAddress());
            ps.setString(4, order.getPaymentMethod());
            ps.setString(5, order.getStatus());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public boolean addOrderItem(OrderItemModel item) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.ADD_ORDER_ITEM)) {
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPriceAtTime());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<OrderModel> getOrdersByUser(int userId) {
        List<OrderModel> orders = new ArrayList<>();
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_USER_ORDERS)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(extractOrder(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_ALL_ORDERS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrderModel order = extractOrder(rs);
                order.setUserName(rs.getString("user_name"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public OrderModel getOrderDetails(int orderId) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_ORDER_DETAILS)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                OrderModel order = extractOrder(rs);
                order.setItems(getOrderItems(orderId));
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<OrderItemModel> getOrderItems(int orderId) {
        List<OrderItemModel> items = new ArrayList<>();
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_ORDER_ITEMS)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItemModel item = new OrderItemModel();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPriceAtTime(rs.getDouble("price_at_time"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public boolean updateOrderStatus(int orderId, String status) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.UPDATE_ORDER_STATUS)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getTotalOrders() {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_TOTAL_ORDERS);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public double getTotalRevenue() {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_TOTAL_REVENUE);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<OrderModel> getRecentOrders(int limit) {
        List<OrderModel> orders = new ArrayList<>();
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_RECENT_ORDERS)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderModel order = extractOrder(rs);
                order.setUserName(rs.getString("user_name"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    private OrderModel extractOrder(ResultSet rs) throws SQLException {
        OrderModel order = new OrderModel();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setStatus(rs.getString("status"));
        order.setShippingAddress(rs.getString("shipping_address"));
        order.setPaymentMethod(rs.getString("payment_method"));
        return order;
    }
}