<%@ page import="com.ecommerce.models.UserModel" %>
    <%@ page session="true" %>
        <% String email=(String) session.getAttribute("email"); String role=(String) session.getAttribute("role");
            boolean isLoggedIn=email !=null; %>
            <header class="header">
                <div class="logo">
                    <a href="${pageContext.request.contextPath}/index.jsp">
                        <img src="${pageContext.request.contextPath}/images/logo.png" alt="Auto Parts Store">
                        <span>AutoParts</span>
                    </a>
                </div>

                <nav class="navbar">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/products">Products</a></li>
                        <li><a href="${pageContext.request.contextPath}/main/about.jsp">About</a></li>
                        <li><a href="${pageContext.request.contextPath}/main/contact.jsp">Contact</a></li>
                    </ul>
                </nav>

                <div class="nav-actions">
                    <% if (isLoggedIn) { %>
                        <% if ("admin".equals(role)) { %>
                            <a href="${pageContext.request.contextPath}/admin-dashboard"
                                class="btn btn-outline">Dashboard</a>
                            <% } else { %>
                                <a href="${pageContext.request.contextPath}/cart" class="cart-icon">
                                    <i class="fas fa-shopping-cart"></i>
                                    <span class="cart-count">${sessionScope.cartCount}</span>
                                </a>
                                <a href="${pageContext.request.contextPath}/my-orders" class="btn btn-outline">My
                                    Orders</a>
                                <% } %>
                                    <div class="user-menu">
                                        <span>Welcome, ${sessionScope.email}</span>
                                        <a href="${pageContext.request.contextPath}/logout"
                                            class="btn btn-danger">Logout</a>
                                    </div>
                                    <% } else { %>
                                        <a href="${pageContext.request.contextPath}/WEB-INF/views/auth/login.jsp"
                                            class="btn btn-outline">Login</a>
                                        <a href="${pageContext.request.contextPath}/WEB-INF/views/auth/register.jsp"
                                            class="btn btn-primary">Register</a>
                                        <% } %>
                </div>
            </header>