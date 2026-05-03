package com.ecommerce.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
    "/WEB-INF/views/admin/*",
    "/WEB-INF/views/user/*",
    "/admin-*",
    "/user-*"
})
public class AuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        
        String path = request.getRequestURI();
        boolean isAuthPage = path.endsWith("/login.jsp") || path.endsWith("/register.jsp");
        boolean isLoggedIn = session != null && session.getAttribute("email") != null;
        String role = session != null ? (String) session.getAttribute("role") : null;
        
        // If logged in and trying to access auth pages, redirect to dashboard
        if (isAuthPage && isLoggedIn) {
            if ("admin".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/WEB-INF/views/admin/dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/WEB-INF/views/user/home.jsp");
            }
            return;
        }
        
        // If not logged in and trying to access protected pages, redirect to login
        if (!isAuthPage && !isLoggedIn) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/views/auth/login.jsp");
            return;
        }
        
        // Check admin access
        if (path.contains("/admin/") && !"admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/notFound.jsp");
            return;
        }
        
        chain.doFilter(req, res);
    }
}