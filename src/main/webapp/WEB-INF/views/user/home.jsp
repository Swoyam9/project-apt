<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main>
    <section class="hero">
        <div>
            <p class="eyebrow">Automobile Spare Parts</p>
            <h1>Find reliable parts for repairs, servicing, and upgrades.</h1>
            <a class="button" href="${pageContext.request.contextPath}/products">Browse Products</a>
        </div>
    </section>
    <section class="section">
        <div class="section-title">
            <h2>Latest Parts</h2>
            <a href="${pageContext.request.contextPath}/products">View all</a>
        </div>
        <div class="product-grid">
            <c:forEach var="product" items="${products}" end="5">
                <article class="product-card">
                    <div class="product-image">${product.categoryName}</div>
                    <h3>${product.name}</h3>
                    <p>${product.brand} · ${product.partNumber}</p>
                    <strong>Nrs <fmt:formatNumber value="${product.price}" minFractionDigits="2"/></strong>
                    <form method="post" action="${pageContext.request.contextPath}/cart/add">
                        <input type="hidden" name="productId" value="${product.id}">
                        <input type="hidden" name="quantity" value="1">
                        <button class="button small" type="submit">Add to Cart</button>
                    </form>
                </article>
            </c:forEach>
        </div>
    </section>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
