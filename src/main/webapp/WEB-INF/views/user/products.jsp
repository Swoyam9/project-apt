<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
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
                <c:set var="productImage" value="default-part.jpg"/>
                <c:if test="${product.categoryName eq 'Engine Parts'}"><c:set var="productImage" value="engine-filter.jpg"/></c:if>
                <c:if test="${product.categoryName eq 'Brake System'}"><c:set var="productImage" value="brake-pads.jpg"/></c:if>
                <c:if test="${product.categoryName eq 'Electrical'}"><c:set var="productImage" value="battery.jpg"/></c:if>
                <c:if test="${product.categoryName eq 'Suspension'}"><c:set var="productImage" value="shock-absorber.png"/></c:if>
                <c:if test="${product.categoryName eq 'Body Parts'}"><c:set var="productImage" value="side-mirror.jpg"/></c:if>
                <div class="product-image">
                    <img src="${pageContext.request.contextPath}/images/products/${productImage}" alt="${product.name}">
                </div>
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
