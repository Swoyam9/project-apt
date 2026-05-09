<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:set var="hideLoginNav" value="true" scope="request"/>
<jsp:include page="/components/header.jsp"/>
<main class="auth-shell login-page">
    <section class="auth-card">
        <p class="eyebrow form-eyebrow">Welcome back</p>
        <h1>Login</h1>
        <form method="post" action="${pageContext.request.contextPath}/login" class="form">
            <div class="login-switch" aria-label="Login type">
                <label>
                    <input type="radio" name="loginType" value="user" checked>
                    <span>User</span>
                </label>
                <label>
                    <input type="radio" name="loginType" value="admin">
                    <span>Admin</span>
                </label>
            </div>
            <label>Email
                <input type="email" name="email" required>
            </label>
            <label>Password
                <input type="password" name="password" required>
            </label>
            <label class="checkbox">
                <input type="checkbox" name="remember"> Remember me
            </label>
            <button class="button" type="submit">Login</button>
        </form>
        <p class="muted">New customer? <a href="${pageContext.request.contextPath}/register">Create an account</a></p>
    </section>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
