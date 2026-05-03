package com.ecommerce.controller;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.ResultModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        
        ResultModel result = userDAO.loginUser(email, password);
        
        if (result.isSuccess()) {
            req.getSession().setAttribute("email", result.getEmail());
            req.getSession().setAttribute("role", result.getRole());
            req.getSession().setAttribute("active", result.getActive());
            
            if ("admin".equals(result.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin-dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/user-home");
            }
        } else {
            req.setAttribute("error", result.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
        }
    }
}