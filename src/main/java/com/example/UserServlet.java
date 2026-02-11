package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/user", "/user/*"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        if (name == null) name = "JohnDoe";

        String pathInfo = request.getPathInfo(); 
        String userId = (pathInfo != null && pathInfo.length() > 1) 
                        ? pathInfo.substring(1) : "DefaultID";

        HttpSession session = request.getSession();
        session.setAttribute("username", name);

        Cookie userCookie = new Cookie("userRole", "admin");
        userCookie.setMaxAge(3600); 
        response.addCookie(userCookie);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<p><b>Параметр (name):</b> " + name + "</p>");
        out.println("<p><b>Змінна (ID):</b> " + userId + "</p>");
        out.println("<p><b>Session Data:</b> " + session.getAttribute("username") + "</p>");
        out.println("<p><b>Cookie:</b> 'userRole' відправлено у браузер.</p>");
        
        out.println("<hr><form method='POST' action='/lab1/user'>");
        out.println("<button type='submit'>POST запит</button>");
        out.println("</form></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String sessionUser = (String) request.getSession().getAttribute("username");

        PrintWriter out = response.getWriter();
        out.print("{");
        out.print("\"status\": \"success\",");
        out.print("\"user_in_session\": \"" + sessionUser + "\",");
        out.print("\"message\": \"JSON дані оброблені POST\"");
        out.print("}");
        out.flush();
    }
}