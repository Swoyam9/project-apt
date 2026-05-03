package com.ecommerce.dao;

import com.ecommerce.models.UserModel;
import com.ecommerce.models.ResultModel;
import com.ecommerce.utils.DatabaseConn;
import com.ecommerce.utils.PasswordUtils;
import com.ecommerce.queries.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public ResultModel loginUser(String email, String password) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.CHECK_USER_LOGIN)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (PasswordUtils.checkPassword(password, hashedPassword)) {
                    return new ResultModel(true, "Login successful", 
                        rs.getString("email"), rs.getString("role"), rs.getString("active"));
                }
            }
            return new ResultModel(false, "Invalid email or password");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel(false, "Database error: " + e.getMessage());
        }
    }
    
    public UserModel getUserByEmail(String email) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_USER_BY_EMAIL)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getString("active"));
                user.setCreatedDate(rs.getTimestamp("created_date"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ResultModel registerUser(UserModel user) {
        try (Connection conn = DatabaseConn.getConnection()) {
            
            // Check if email exists
            PreparedStatement checkStmt = conn.prepareStatement(Queries.CHECK_EMAIL_EXISTS);
            checkStmt.setString(1, user.getEmail());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return new ResultModel(false, "Email already exists");
            }
            
            // Register user
            PreparedStatement ps = conn.prepareStatement(Queries.REGISTER_USER);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, PasswordUtils.hashPassword(user.getPassword()));
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getRole());
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return new ResultModel(true, "Registration successful");
            }
            return new ResultModel(false, "Registration failed");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel(false, "Error: " + e.getMessage());
        }
    }
    
    public List<UserModel> getAllUsers() {
        List<UserModel> users = new ArrayList<>();
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_ALL_USERS);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getString("active"));
                user.setCreatedDate(rs.getTimestamp("created_date"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public boolean updateUserStatus(int userId, String status) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.UPDATE_USER_STATUS)) {
            ps.setString(1, status);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}