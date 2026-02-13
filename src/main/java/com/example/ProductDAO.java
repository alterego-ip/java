package com.example;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final DataSource dataSource = DBConnection.getDataSource();

    public void addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO products (name, brand, category, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, p.getName());
                pstmt.setString(2, p.getBrand());
                pstmt.setString(3, p.getCategory());
                pstmt.setInt(4, p.getQuantity());
                pstmt.setDouble(5, p.getPrice());
                pstmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("brand"),
                        rs.getString("category"), rs.getInt("quantity"), rs.getDouble("price")));
            }
        }
        return products;
    }

    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(rs.getInt("id"), rs.getString("name"), rs.getString("brand"),
                            rs.getString("category"), rs.getInt("quantity"), rs.getDouble("price"));
                }
            }
        }
        return null;
    }

    public void updateProduct(Product p) throws SQLException {
        String sql = "UPDATE products SET name=?, brand=?, category=?, quantity=?, price=? WHERE id=?";
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, p.getName());
                pstmt.setString(2, p.getBrand());
                pstmt.setString(3, p.getCategory());
                pstmt.setInt(4, p.getQuantity());
                pstmt.setDouble(5, p.getPrice());
                pstmt.setInt(6, p.getId());
                pstmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}