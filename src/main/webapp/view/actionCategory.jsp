<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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
    <title>Category Action Page</title>
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

		<div class="category-form-wrapper">
			<div class="category-form-container">
		        <h1>${action.equals("update") ? "Cập nhật danh mục" : "Thêm mới danh mục"}</h1>
		        <form action="${action.equals('update') ? '/Order/updateCategory' : '/Order/addCategory'}" method="post">
		            <c:if test="${action.equals('update')}">
		                <input type="hidden" name="categoryID" value="${category.categoryID}">
		            </c:if>
		            <div class="form-group">
		                <label for="categoryName">Tên danh mục:</label>
		                <input type="text" id="categoryName" name="categoryName" value="${category.categoryName}" required>
		            </div>
		            <div class="form-group">
		                <label for="categoryDescription">Mô tả:</label>
		                <textarea id="categoryDescription" name="categoryDescription" required>${category.categoryDescription}</textarea>
		            </div>
		            <button type="submit">${action.equals("update") ? "Cập nhật" : "Thêm mới"}</button>
		        </form>
	   		</div>
		</div>
    </div>
</body>
</html>
