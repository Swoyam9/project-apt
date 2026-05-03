package com.ecommerce.controller;

import com.ecommerce.dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/products/delete")
public class DeleteProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            productDAO.delete(id);
            request.getSession().setAttribute("success", "Product deleted successfully.");
            response.sendRedirect(request.getContextPath() + "/admin/products");
        } catch (SQLException | NumberFormatException e) {
            request.getSession().setAttribute("error", "Product cannot be deleted if it is already used in orders.");
            response.sendRedirect(request.getContextPath() + "/admin/products");
        }
    }
}
