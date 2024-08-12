<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("/Order/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang Quản Lý Đơn Hàng</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/base.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/adminOrder.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/leftsidebar.css" type="text/css">
    
    <!-- Logo -->
    <link rel="icon" href="${pageContext.request.contextPath}/resource/file/logo.jpg" type="image/jpg">
   
    <!-- JS -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/adminOrder.js"></script>
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
    <script>
	    function extractTime(dateTimeString) {
	        // Tách chuỗi theo chữ "T"
	        var parts = dateTimeString.split("T");
	        if (parts.length === 2) {
	            return parts[1]; // Lấy phần thời gian sau chữ "T"
	        }
	        return dateTimeString; // Nếu không có "T", trả về chuỗi ban đầu
	    }
	</script>
</head>
<body>
    <jsp:include page="/view/header/loginHeader.jsp"></jsp:include>
    <div class="order-main-container">
        <jsp:include page="/view/sidebar/leftSidebar.jsp"></jsp:include>
        
        <div class="order-content">
            <h2>Đơn Hàng Chờ Xử Lý</h2>
            <div id="pending-orders" class="order-section">
                <c:forEach var="order" items="${orders}">
                    <c:if test="${order.billStatus == 3}">
                        <div class="order-item background-red" data-id="${order.billID}">
                            <div class="order-details">
                                <p>Bàn số: <span>${order.tableID}</span></p>
								<c:set var="dateTime" value="${order.billDateCreate}" />
								<p>Thời gian: <span><script>document.write(extractTime("${dateTime}"));</script></span></p>
                                <table>
                                    <thead>
									    <tr>
									        <th>Sản phẩm</th>
									        <th>Số lượng</th>
									    </tr>
									</thead>
									<tbody>
									    <c:forEach var="detail" items="${order.details}">
									        <tr>
									            <td>${detail.productName}</td> <!-- Thay đổi từ productID thành productName -->
									            <td>${detail.billDetailQuantity}</td>
									        </tr>
									    </c:forEach>
									</tbody>
                                </table>
                                <p>Ghi chú: <span>${order.billNote}</span></p>
                            </div>
                            <div class="order-actions">
                                <form action="AdminOrderAction?action=confirm" method="post">
                                    <input type="hidden" name="billID" value="${order.billID}">
                                    <button type="submit">Xác nhận</button>
                                </form>
                                <form action="AdminOrderAction?action=delete" method="post">
                                    <input type="hidden" name="billID" value="${order.billID}">
                                    <button type="submit" class="delete-btn">Hủy đơn</button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <h2>Đơn Hàng Đã Xác Nhận</h2>
            <div id="confirmed-orders" class="order-section">
                <c:forEach var="order" items="${orders}">
                    <c:if test="${order.billStatus == 2}">
                        <div class="order-item background-yellow" data-id="${order.billID}">
                            <div class="order-details">
                                <p>Bàn số: <span>${order.tableID}</span></p>
                                <p>Thời gian: <span>${order.billDateCreate}</span></p>
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Sản phẩm</th>
                                            <th>Số lượng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="detail" items="${order.details}">
                                            <tr>
                                                <td>${detail.productID}</td>
                                                <td>${detail.billDetailQuantity}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <p>Ghi chú: <span>${order.billNote}</span></p>
                            </div>
                            <div class="order-actions">
                                <form action="AdminOrderAction?action=complete" method="post">
                                    <input type="hidden" name="billID" value="${order.billID}">
                                    <button type="submit">Thanh toán</button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>
