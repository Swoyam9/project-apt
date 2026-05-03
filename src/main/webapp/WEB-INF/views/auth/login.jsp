<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Login - Auto Parts Store</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        </head>

        <body>
            <%@ include file="../../components/navbar.jsp" %>

                <div class="auth-container">
                    <div class="auth-card">
                        <h2>Login to Your Account</h2>

                        <c:if test="${not empty param.error}">
                            <div class="alert alert-error">${param.error}</div>
                        </c:if>
                        <c:if test="${not empty requestScope.error}">
                            <div class="alert alert-error">${requestScope.error}</div>
                        </c:if>
                        <c:if test="${not empty requestScope.success}">
                            <div class="alert alert-success">${requestScope.success}</div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">
                            <div class="form-group">
                                <label for="email">Email Address</label>
                                <div class="input-group">
                                    <i class="fas fa-envelope"></i>
                                    <input type="email" id="email" name="email" placeholder="Enter your email" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="password">Password</label>
                                <div class="input-group">
                                    <i class="fas fa-lock"></i>
                                    <input type="password" id="password" name="password"
                                        placeholder="Enter your password" required>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-primary btn-block">Login</button>
                        </form>

                        <div class="auth-footer">
                            <p>Don't have an account? <a
                                    href="${pageContext.request.contextPath}/WEB-INF/views/auth/register.jsp">Register
                                    here</a></p>
                            <a href="${pageContext.request.contextPath}/index.jsp">Back to Home</a>
                        </div>
                    </div>
                </div>

                <%@ include file="../../components/footer.jsp" %>
        </body>

        </html>