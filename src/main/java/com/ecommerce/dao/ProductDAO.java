package com.ecommerce.dao;

import com.ecommerce.models.ProductModel;
import com.ecommerce.utils.DatabaseConn;
import com.ecommerce.queries.Queries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    
    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_ALL_PRODUCTS);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                products.add(extractProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public ProductModel getProductById(int productId) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_PRODUCT_BY_ID)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractProduct(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addProduct(ProductModel product) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.ADD_PRODUCT)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStockQuantity());
            ps.setInt(5, product.getCategoryId());
            ps.setString(6, product.getManufacturer());
            ps.setString(7, product.getModelCompatibility());
            ps.setString(8, product.getImageUrl());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateProduct(ProductModel product) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.UPDATE_PRODUCT)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStockQuantity());
            ps.setInt(5, product.getCategoryId());
            ps.setString(6, product.getManufacturer());
            ps.setString(7, product.getModelCompatibility());
            ps.setString(8, product.getImageUrl());
            ps.setInt(9, product.getProductId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteProduct(int productId) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.DELETE_PRODUCT)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<ProductModel> searchProducts(String keyword) {
        List<ProductModel> products = new ArrayList<>();
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.SEARCH_PRODUCTS)) {
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(extractProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public List<ProductModel> getProductsByCategory(int categoryId) {
        List<ProductModel> products = new ArrayList<>();
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_PRODUCTS_BY_CATEGORY)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(extractProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public boolean updateStock(int productId, int quantity) {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.UPDATE_PRODUCT_STOCK)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getTotalProducts() {
        try (Connection conn = DatabaseConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(Queries.GET_TOTAL_PRODUCTS);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private ProductModel extractProduct(ResultSet rs) throws SQLException {
        ProductModel product = new ProductModel();
        product.setProductId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setCategoryName(rs.getString("category_name"));
        product.setManufacturer(rs.getString("manufacturer"));
        product.setModelCompatibility(rs.getString("model_compatibility"));
        product.setImageUrl(rs.getString("image_url"));
        product.setCreatedDate(rs.getTimestamp("created_date"));
        return product;
    }
}