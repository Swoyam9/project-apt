<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="site-header">
    <a class="brand" href="${pageContext.request.contextPath}/home">AutoSpares</a>
    <nav class="nav">
        <c:if test="${not hideLoginNav}">
            <a href="${pageContext.request.contextPath}/products">Products</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser && sessionScope.authUser.role eq 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/admin/dashboard">Admin</a>
            <a href="${pageContext.request.contextPath}/admin/products">Manage Products</a>
        </c:if>
        <c:if test="${not empty sessionScope.authUser}">
            <a href="${pageContext.request.contextPath}/cart">Cart</a>
            <a href="${pageContext.request.contextPath}/orders">Orders</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:if>
        <c:if test="${empty sessionScope.authUser}">
            <c:if test="${not hideLoginNav}">
                <a href="${pageContext.request.contextPath}/login">Login</a>
            </c:if>
            <a class="button small" href="${pageContext.request.contextPath}/register">Register</a>
        </c:if>
    </nav>
</header>

<c:if test="${not empty sessionScope.success}">
    <div class="alert success">${sessionScope.success}</div>
    <c:remove var="success" scope="session"/>
</c:if>
<c:if test="${not empty sessionScope.error}">
    <div class="alert error">${sessionScope.error}</div>
    <c:remove var="error" scope="session"/>
</c:if>
<c:if test="${not empty success}">
    <div class="alert success">${success}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert error">${error}</div>
</c:if>
