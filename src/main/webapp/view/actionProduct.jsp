<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
    <title>${action.equals("update") ? "Cập nhật sản phẩm" : "Thêm mới sản phẩm"}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- css -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/base.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/product.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/leftsidebar.css" type="text/css">
    
    <!-- logo -->
    <link rel="icon" href="${pageContext.request.contextPath}/resource/file/logo.jpg" type="image/jpg">
    
    <!-- js -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/product.js"></script>
</head>
<body>
    <jsp:include page="/view/header/loginHeader.jsp"></jsp:include>

    <div class="product-main-container">
        <jsp:include page="/view/sidebar/leftSidebar.jsp"></jsp:include>

        <div class="product-form-wrapper">
            <div class="product-form-container">
                <h1>${action.equals("update") ? "Cập nhật sản phẩm" : "Thêm mới sản phẩm"}</h1>
                <form action="${action.equals('update') ? '/Order/updateProduct' : '/Order/addProduct'}" method="post" enctype="multipart/form-data">
                    <c:if test="${action.equals('update')}">
                        <input type="hidden" name="productID" value="${product.product.productID}">
                    </c:if>
                    <div class="form-group form-image-session">    
                        <c:if test="${action.equals('update') && not empty product.product.productImage}">
                            <p>Hình ảnh hiện tại:</p>
                            <img src="${pageContext.request.contextPath}/images/${product.product.productImage}" alt="Product Image" width="100">
                        </c:if>
                        <label for="productImage">Chọn hình ảnh:</label>
                        <input type="file" id="productImage" name="productImage">
                    </div>
                    <div class="form-group">
                        <label for="productName">Tên sản phẩm:</label>
                        <input type="text" id="productName" name="productName" value="${fn:escapeXml(product.product.productName)}" required>
                    </div>
                    <div class="form-group">
                        <label for="category">Loại sản phẩm:</label>
                        <select id="category" name="categoryID" required>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.categoryID}" ${category.categoryID == product.product.categoryID ? 'selected' : ''}>
                                    ${fn:escapeXml(category.categoryName)}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <c:if test="${action.equals('add')}">
                        <div class="form-group">
                            <label for="productImportPrice">Giá nhập:</label>
                            <input type="number" id="productImportPrice" name="productImportPrice" step="0.01" value="${fn:escapeXml(product.product.productImportPrice)}" required>
                        </div>
                    </c:if>
                    <c:if test="${action.equals('update')}">
                        <div class="form-group">
                            <label for="productImportPrice">Giá nhập:</label>
                            <label style = "margin-left: 10px;">${fn:escapeXml(product.product.productImportPrice)}</label>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <label for="productSalePrice">Giá bán:</label>
                        <input type="number" id="productSalePrice" name="productSalePrice" step="0.01" value="${fn:escapeXml(product.product.productSalePrice)}" required>
                    </div>
                    <button type="submit">${action.equals("update") ? "Cập nhật" : "Thêm mới"}</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
