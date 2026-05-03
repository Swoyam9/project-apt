package com.ecommerce.controller;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.models.CartItem;
import com.ecommerce.models.User;
import com.ecommerce.utils.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/order/place")
public class PlaceOrderServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String shippingAddress = request.getParameter("shippingAddress");
        List<CartItem> cart = (List<CartItem>) request.getSession().getAttribute("cart");
        User user = (User) request.getSession().getAttribute("authUser");

        if (ValidationUtils.isBlank(shippingAddress) || cart == null || cart.isEmpty()) {
            request.getSession().setAttribute("error", "Please provide a shipping address and add items to your cart.");
            response.sendRedirect(request.getContextPath() + "/checkout");
            return;
        }

        try {
            int orderId = orderDAO.createOrder(user.getId(), shippingAddress, cart);
            request.getSession().removeAttribute("cart");
            request.getSession().setAttribute("success", "Order #" + orderId + " placed successfully.");
            response.sendRedirect(request.getContextPath() + "/orders");
        } catch (SQLException e) {
            request.getSession().setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}
