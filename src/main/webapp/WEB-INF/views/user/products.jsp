<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Products - Auto Parts Store</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
            </head>

            <body>
                <%@ include file="../../components/navbar.jsp" %>

                    <div class="products-page">
                        <div class="container">
                            <h1>Our Products</h1>

                            <div class="search-bar">
                                <form action="${pageContext.request.contextPath}/search-products" method="get">
                                    <input type="text" name="keyword" placeholder="Search products..."
                                        value="${param.keyword}">
                                    <button type="submit" class="btn btn-primary">Search</button>
                                </form>
                            </div>

                            <div class="products-grid">
                                <c:forEach var="product" items="${products}">
                                    <div class="product-card">
                                        <div class="product-image">
                                            <img src="${product.imageUrl != null ? product.imageUrl : 'https://via.placeholder.com/200'}"
                                                alt="${product.name}">
                                        </div>
                                        <div class="product-info">
                                            <h3>${product.name}</h3>
                                            <p class="product-price">$${product.price}</p>
                                            <p class="product-stock">In Stock: ${product.stockQuantity}</p>
                                            <p class="product-manufacturer">${product.manufacturer}</p>
                                            <c:if test="${product.stockQuantity > 0}">
                                                <form action="${pageContext.request.contextPath}/add-to-cart"
                                                    method="post">
                                                    <input type="hidden" name="productId" value="${product.productId}">
                                                    <input type="number" name="quantity" value="1" min="1"
                                                        max="${product.stockQuantity}">
                                                    <button type="submit" class="btn btn-primary">Add to Cart</button>
                                                </form>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <c:if test="${empty products}">
                                <p class="no-results">No products found.</p>
                            </c:if>
                        </div>
                    </div>

                    <%@ include file="../../components/footer.jsp" %>
            </body>

            </html>