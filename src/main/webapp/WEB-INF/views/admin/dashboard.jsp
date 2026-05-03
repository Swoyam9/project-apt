<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section">
    <div class="section-title">
        <h1>Admin Dashboard</h1>
        <a class="button small" href="${pageContext.request.contextPath}/admin/products/add">Add Product</a>
    </div>
    <div class="dashboard-grid">
        <div class="stat-card"><span>Total Products</span><strong>${fn:length(products)}</strong></div>
        <div class="stat-card"><span>Low Stock</span><strong><c:set var="low" value="0"/><c:forEach var="p" items="${products}"><c:if test="${p.stockQuantity lt 10}"><c:set var="low" value="${low + 1}"/></c:if></c:forEach>${low}</strong></div>
        <div class="stat-card"><span>Categories</span><strong>5</strong></div>
    </div>
    <h2>Recent Products</h2>
    <jsp:include page="/WEB-INF/views/admin/products.jsp">
        <jsp:param name="embedded" value="true"/>
    </jsp:include>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
