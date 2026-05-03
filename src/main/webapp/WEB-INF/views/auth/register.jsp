<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - AutoSpares</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>
<main class="auth-shell">
    <section class="auth-card wide">
        <h1>Create Account</h1>
        <form method="post" action="${pageContext.request.contextPath}/register" enctype="multipart/form-data" class="form grid-form">
            <label>Full name
                <input type="text" name="fullName" required>
            </label>
            <label>Email
                <input type="email" name="email" required>
            </label>
            <label>Phone
                <input type="tel" name="phone" required>
            </label>
            <label>Profile photo
                <input type="file" name="profileImage" accept="image/*">
            </label>
            <label class="span-2">Address
                <textarea name="address" rows="3"></textarea>
            </label>
            <label>Password
                <input type="password" name="password" minlength="6" required>
            </label>
            <label>Confirm password
                <input type="password" name="confirmPassword" minlength="6" required>
            </label>
            <button class="button span-2" type="submit">Register</button>
        </form>
    </section>
</main>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>
