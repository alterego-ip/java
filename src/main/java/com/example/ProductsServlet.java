package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

private Connection getConnection() throws SQLException {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    String url = "jdbc:mysql://localhost:3306/tech_shop?useSSL=false&serverTimezone=UTC";
    String user = "labuser";
    String password = "root";
    return DriverManager.getConnection(url, user, password);
}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            out.println("""
                <html>
                <head>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background: #f4f6f8;
                            margin: 0;
                            padding: 20px;
                        }
                        h2 {
                            text-align: center;
                        }
                        .cards {
                            display: flex;
                            flex-wrap: wrap;
                            gap: 20px;
                            justify-content: center;
                        }
                        .card {
                            background: white;
                            width: 250px;
                            padding: 15px;
                            border-radius: 10px;
                            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                        }
                        .card h3 {
                            margin: 0 0 10px;
                        }
                        .price {
                            color: green;
                            font-weight: bold;
                        }
                        form {
                            margin-top: 40px;
                            text-align: center;
                            background: white;
                            padding: 20px;
                            border-radius: 10px;
                            width: 300px;
                            margin-left: auto;
                            margin-right: auto;
                            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                        }
                        input {
                            width: 90%;
                            padding: 8px;
                            margin: 5px 0;
                        }
                        button {
                            padding: 10px 15px;
                            margin-top: 10px;
                            cursor: pointer;
                        }
                    </style>
                </head>
                <body>
                <h2>Каталог техніки</h2>
                <div class='cards'>
            """);

            while (rs.next()) {
                out.println("<div class='card'>");
                out.println("<h3>" + rs.getString("name") + "</h3>");
                out.println("<p>Бренд: " + rs.getString("brand") + "</p>");
                out.println("<p>Категорія: " + rs.getString("category") + "</p>");
                out.println("<p>Кількість: " + rs.getInt("quantity") + "</p>");
                out.println("<p class='price'>Ціна: " + rs.getDouble("price") + " грн</p>");
                out.println("</div>");
            }

            out.println("</div>");

            out.println("""
                <form method='POST' action='products'>
                    <h3>Додати товар</h3>
                    <input type='text' name='name' placeholder='Назва'><br>
                    <input type='text' name='brand' placeholder='Бренд'><br>
                    <input type='text' name='category' placeholder='Категорія'><br>
                    <input type='number' name='quantity' placeholder='Кількість'><br>
                    <input type='number' step='0.01' name='price' placeholder='Ціна'><br>
                    <button type='submit'>Додати</button>
                </form>
                </body>
                </html>
            """);

        } catch (SQLException e) {
            out.println("Помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        String category = request.getParameter("category");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));

        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO products (name, brand, category, quantity, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, brand);
            pstmt.setString(3, category);
            pstmt.setInt(4, quantity);
            pstmt.setDouble(5, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("products");
    }
}
