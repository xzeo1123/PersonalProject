<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Trang Đăng Nhập</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/base.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/login.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
    
    <!-- logo -->
    <link rel="icon" href="${pageContext.request.contextPath}/resource/file/logo.jpg" type="image/jpg">
</head>

<body>
    <jsp:include page="/view/header/logoutHeader.jsp"></jsp:include>

    <h1>ĐĂNG NHẬP</h1>

    <main>
        <form class="login-container" method="post" action="/Order/admin">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" value="<%= request.getAttribute("enteredUsername") != null ? request.getAttribute("enteredUsername") : "" %>">

            <label for="password">Password</label>
            <input type="password" id="password" name="password" value="<%= request.getAttribute("enteredPassword") != null ? request.getAttribute("enteredPassword") : "" %>">

            <button type="submit" id="loginButton">Đăng Nhập</button>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <script type="text/javascript">
                    alert('<%= request.getAttribute("errorMessage") %>');
                </script>
            <% } %>
        </form>
    </main>

</body>
</html>
