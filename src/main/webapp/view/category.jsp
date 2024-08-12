<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="entity.Category"%>
<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("/Order/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Category Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- css -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/base.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/category.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/leftsidebar.css" type="text/css">
    
    <!-- logo -->
    <link rel="icon" href="${pageContext.request.contextPath}/resource/file/logo.jpg" type="image/jpg">
   
	<!-- js -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/category.js"></script>
</head>
<body>
    <jsp:include page="/view/header/loginHeader.jsp"></jsp:include>

    <div class="category-main-container">
        <jsp:include page="/view/sidebar/leftSidebar.jsp"></jsp:include>

        <main class="category-content">
            <div class="category-controls">
                <button class="add-btn"><i class="fas fa-plus"></i> Thêm mới</button>
                <div class="search">
                    <input type="text" class="search-input" placeholder="Tìm kiếm...">
                </div>
            </div>

            
            <div class="category-items">
                <!-- Category items dynamically injected here -->
            </div>

            <div class="pagination">
                <!-- Pagination dynamically injected here -->
            </div>
        </main>
    </div>
</body>
</html>
