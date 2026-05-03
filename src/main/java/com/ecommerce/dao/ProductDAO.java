package com.ecommerce.dao;

import com.ecommerce.models.Product;
import com.ecommerce.queries.Queries;
import com.ecommerce.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.LIST_PRODUCTS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        }
        return products;
    }

    public Product findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.FIND_PRODUCT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapProduct(rs) : null;
            }
        }
    }

    public boolean create(Product product) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.INSERT_PRODUCT)) {
            fillProductStatement(ps, product);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean update(Product product) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.UPDATE_PRODUCT)) {
            fillProductStatement(ps, product);
            ps.setInt(9, product.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(Queries.DELETE_PRODUCT)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public Map<Integer, String> findCategories() throws SQLException {
        Map<Integer, String> categories = new LinkedHashMap<>();
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(Queries.LIST_CATEGORIES)) {
            while (rs.next()) {
                categories.put(rs.getInt("id"), rs.getString("name"));
            }
        }
        return categories;
    }

    private void fillProductStatement(PreparedStatement ps, Product product) throws SQLException {
        ps.setInt(1, product.getCategoryId());
        ps.setString(2, product.getName());
        ps.setString(3, product.getBrand());
        ps.setString(4, product.getPartNumber());
        ps.setString(5, product.getDescription());
        ps.setBigDecimal(6, product.getPrice());
        ps.setInt(7, product.getStockQuantity());
        ps.setString(8, product.getImagePath());
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setCategoryName(rs.getString("category_name"));
        product.setName(rs.getString("name"));
        product.setBrand(rs.getString("brand"));
        product.setPartNumber(rs.getString("part_number"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        product.setImagePath(rs.getString("image_path"));
        if (rs.getTimestamp("created_at") != null) {
            product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        return product;
    }
}
