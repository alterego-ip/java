package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/edit")
public class EditProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Product p = productDAO.getProductById(id);
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><style>body{font-family:Arial; display:flex; justify-content:center; padding:50px; background:#f4f6f8;}" +
                "form{background:white; padding:30px; border-radius:10px; width:300px; box-shadow:0 4px 10px rgba(0,0,0,0.1);}" +
                "input{width:100%; padding:10px; margin:10px 0; box-sizing:border-box; border:1px solid #ccc; border-radius:5px;}" +
                "button{width:100%; padding:10px; background:#28a745; color:white; border:none; border-radius:5px; cursor:pointer;}" +
                "</style></head><body>");

            if (p != null) {
                out.println("<form method='POST'>");
                out.println("<h2>Редагування</h2>");
                out.println("<input type='hidden' name='id' value='"+p.getId()+"'>");
                out.println("Назва: <input name='name' value='"+p.getName()+"'>");
                out.println("Бренд: <input name='brand' value='"+p.getBrand()+"'>");
                out.println("Категорія: <input name='category' value='"+p.getCategory()+"'>");
                out.println("Кількість: <input type='number' name='quantity' value='"+p.getQuantity()+"'>");
                out.println("Ціна: <input type='number' step='0.01' name='price' value='"+p.getPrice()+"'>");
                out.println("<button type='submit'>Зберегти зміни</button>");
                out.println("<p style='text-align:center'><a href='products'>Назад</a></p></form>");
            }
            out.println("</body></html>");
        } catch (SQLException e) { throw new ServletException(e); }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Product p = new Product(Integer.parseInt(request.getParameter("id")), request.getParameter("name"),
                request.getParameter("brand"), request.getParameter("category"),
                Integer.parseInt(request.getParameter("quantity")), Double.parseDouble(request.getParameter("price")));
            productDAO.updateProduct(p);
        } catch (SQLException e) { e.printStackTrace(); }
        response.sendRedirect("products");
    }
}