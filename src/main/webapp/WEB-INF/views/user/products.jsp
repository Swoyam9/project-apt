<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Products - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="section">
    <div class="section-title">
        <h1>Spare Parts Catalog</h1>
        <span class="muted">${fn:length(products)} products</span>
    </div>
    <div class="product-grid">
        <c:forEach var="product" items="${products}">
            <article class="product-card">
                <div class="product-image">${product.categoryName}</div>
                <h3>${product.name}</h3>
                <p>${product.description}</p>
                <p class="muted">${product.brand} · ${product.partNumber}</p>
                <strong>Nrs <fmt:formatNumber value="${product.price}" minFractionDigits="2"/></strong>
                <span class="stock">${product.stockQuantity} in stock</span>
                <form method="post" action="${pageContext.request.contextPath}/cart/add" class="inline-form">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="number" name="quantity" min="1" max="${product.stockQuantity}" value="1">
                    <button class="button small" type="submit">Add</button>
                </form>
            </article>
        </c:forEach>
    </div>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
