<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Admin Dashboard - Auto Parts Store</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
            </head>

            <body>
                <%@ include file="../../components/navbar.jsp" %>

                    <div class="admin-container">
                        <div class="admin-sidebar">
                            <ul>
                                <li><a href="${pageContext.request.contextPath}/admin-dashboard" class="active"><i
                                            class="fas fa-dashboard"></i> Dashboard</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin-products"><i
                                            class="fas fa-box"></i> Products</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin-orders"><i
                                            class="fas fa-shopping-cart"></i> Orders</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin-users"><i
                                            class="fas fa-users"></i> Users</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin-categories"><i
                                            class="fas fa-tags"></i> Categories</a></li>
                            </ul>
                        </div>

                        <div class="admin-content">
                            <h1>Dashboard</h1>

                            <div class="stats-grid">
                                <div class="stat-card">
                                    <i class="fas fa-box"></i>
                                    <h3>${totalProducts}</h3>
                                    <p>Total Products</p>
                                </div>
                                <div class="stat-card">
                                    <i class="fas fa-users"></i>
                                    <h3>${totalUsers}</h3>
                                    <p>Total Users</p>
                                </div>
                                <div class="stat-card">
                                    <i class="fas fa-shopping-cart"></i>
                                    <h3>${totalOrders}</h3>
                                    <p>Total Orders</p>
                                </div>
                                <div class="stat-card">
                                    <i class="fas fa-dollar-sign"></i>
                                    <h3>$${totalRevenue}</h3>
                                    <p>Total Revenue</p>
                                </div>
                            </div>

                            <div class="recent-orders">
                                <h2>Recent Orders</h2>
                                <table class="data-table">
                                    <thead>
                                        <tr>
                                            <th>Order ID</th>
                                            <th>Customer</th>
                                            <th>Date</th>
                                            <th>Total</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="order" items="${recentOrders}">
                                            <tr>
                                                <td>#${order.orderId}</td>
                                                <td>${order.userName}</td>
                                                <td>${order.orderDate}</td>
                                                <td>$${order.totalAmount}</td>
                                                <td><span
                                                        class="status-badge status-${order.status}">${order.status}</span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <%@ include file="../../components/footer.jsp" %>
            </body>

            </html>