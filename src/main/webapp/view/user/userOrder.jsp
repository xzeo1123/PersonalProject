<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- css -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/base.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/userOrder.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
    
    <!-- logo -->
    <link rel="icon" href="${pageContext.request.contextPath}/resource/file/logo.jpg" type="image/jpg">
    
    <!-- js -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/userOrder.js"></script>
    
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
</head>
<body>
    <jsp:include page="/view/header/logoutHeader.jsp"></jsp:include>

    <!-- Main Container -->
    <div class="container">
        <!-- Tabs -->
        <div class="tabs">
            <div class="tab active" id="order-tab">Gọi món</div>
            <div class="tab" id="bill-tab">Hóa đơn của tôi</div>
        </div>

        <!-- Order Section -->
        <div class="order-section">
            <!-- Search Field -->
            <div class="search-field">
                <input class="search-input" type="text" placeholder="Tìm món ăn...">
            </div>

            <!-- Categories -->
			<div class="categories">
			    <div class="category" data-id="all">Tất cả</div>
			    <c:forEach var="category" items="${categories}">
			        <div class="category" data-id="${category.categoryID}">${category.categoryName}</div>
			    </c:forEach>
			</div>

            <!-- Items List -->
            <div class="items-list" id="items-list">
  
            </div>
        </div>

        <form id="orderForm" action="${pageContext.request.contextPath}/createOrder" method="post">
			<!-- Order Summary Section -->
			<div class="order-summary">
			    <table class="order-table">
			        <thead>
			            <tr>
			                <th class="name-column">Tên</th>
			                <th class="price-column">Đ.Giá</th>
			                <th class="quantity-column">SL</th>
			                <th class="total-column">Tổng</th>
			                <th class="action-column"></th>
			            </tr>
			        </thead>
			        <tbody id="orderItems">
			            <!-- Items will be dynamically added here -->
		            </tbody>
		            <tfoot>
		                <tr class="total-row">
		                    <td colspan="2">Tổng hóa đơn</td>
		                    <td colspan="3" class="total" id="orderTotal">0 VND</td>
		                </tr>
		            </tfoot>
		        </table>
			        
		        <div class="note-section">
		            <label for="bill-note">Ghi chú:</label>
		            <textarea id="bill-note" name="billNote" rows="4" cols="50"></textarea>
		        </div>
		        
		        <div class="order-buttons">
		            <button type="submit" id="continue-order">Gọi món</button>
		        </div>
		    </div>
		</form>
    </div>
</body>
</html>
