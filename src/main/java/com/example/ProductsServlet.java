package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String deleteId = request.getParameter("delete");
        if (deleteId != null) {
            try {
                productDAO.deleteProduct(Integer.parseInt(deleteId));
            } catch (SQLException e) { e.printStackTrace(); }
        }

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            List<Product> products = productDAO.getAllProducts();
            out.println("<html><head><style>" +
                "body { font-family: Arial, sans-serif; background: #f4f6f8; padding: 20px; }" +
                ".cards { display: flex; flex-wrap: wrap; gap: 20px; justify-content: center; }" +
                ".card { background: white; width: 250px; padding: 15px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }" +
                ".price { color: green; font-weight: bold; }" +
                "form { margin: 40px auto; width: 300px; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }" +
                "input { width: 100%; padding: 8px; margin: 5px 0; box-sizing: border-box; }" +
                "button { width: 100%; padding: 10px; cursor: pointer; background: #007bff; color: white; border: none; border-radius: 5px; }" +
                ".actions { margin-top: 10px; display: flex; justify-content: space-between; }" +
                "</style></head><body><h2>Каталог техніки</h2><div class='cards'>");

            for (Product p : products) {
                out.println(String.format("""
                    <div class='card'>
                        <h3>%s</h3>
                        <p>Бренд: %s</p>
                        <p>Категорія: %s</p>
                        <p>Кількість: %d</p>
                        <p class='price'>Ціна: %.2f грн</p>
                        <div class='actions'>
                            <a href='edit?id=%d'>Редагувати</a>
                            <a href='products?delete=%d' style='color:red;'>Видалити</a>
                        </div>
                    </div>
                    """, p.getName(), p.getBrand(), p.getCategory(), p.getQuantity(), p.getPrice(), p.getId(), p.getId()));
            }

            out.println("</div><form method='POST'><h3>Додати товар</h3>" +
                "<input name='name' placeholder='Назва'><input name='brand' placeholder='Бренд'>" +
                "<input name='category' placeholder='Категорія'><input type='number' name='quantity' placeholder='Кількість'>" +
                "<input type='number' step='0.01' name='price' placeholder='Ціна'><button type='submit'>Додати</button></form></body></html>");

        } catch (SQLException e) { out.println("Помилка: " + e.getMessage()); }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Product p = new Product(0, request.getParameter("name"), request.getParameter("brand"),
                request.getParameter("category"), Integer.parseInt(request.getParameter("quantity")),
                Double.parseDouble(request.getParameter("price")));
            productDAO.addProduct(p);
        } catch (SQLException e) { e.printStackTrace(); }
        response.sendRedirect("products");
    }
}