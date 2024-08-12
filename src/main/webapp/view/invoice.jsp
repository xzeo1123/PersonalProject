<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>Invoice Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- css -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/base.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/invoice.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/layout.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/leftsidebar.css" type="text/css">
    
    <!-- logo -->
    <link rel="icon" href="${pageContext.request.contextPath}/resource/file/logo.jpg" type="image/jpg">
    
    <!-- js -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/invoice.js"></script>
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
</head>
<body>
    <jsp:include page="/view/header/loginHeader.jsp"></jsp:include>
    
    <div class="invoice-main-container">
        <jsp:include page="/view/sidebar/leftSidebar.jsp"></jsp:include>

        <main class="invoice-content">
            <div class="left-section">
                <div class="invoice-controls">
                    <input type="text" class="search-input" placeholder="Tìm kiếm...">
                    <select class="category-filter">
                        <option value="all">Tất cả</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryID}">${category.categoryName}</option>
                        </c:forEach>
                    </select>
                    <div class="quantity-sort">
                        <label><input type="radio" name="sortOrder" value="asc"> SL Tăng dần</label>
                        <label><input type="radio" name="sortOrder" value="desc"> SL Giảm dần</label>
                    </div>
                </div>

                <div class="product-list">
                    <table>
                        <thead>
                            <tr>
                                <th>Ảnh</th>
                                <th>Tên</th>
                                <th>Giá nhập</th>
                                <th>Tồn kho</th>
                            </tr>
                        </thead>
                        <tbody id="product-table-body">
                            <c:forEach var="product" items="${products}">
                                <tr class="product-row" data-product-id="${product.productID}" data-product-name="${product.productName}" data-product-price="${product.productImportPrice}">
                                    <td><img src="${pageContext.request.contextPath}/images/${product.productImage}" alt="Product Image" class="product-image"></td>
                                    <td>${product.productName}</td>
                                    <td>${product.productImportPrice}</td>
                                    <td>${product.productQuantity}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="right-section">
                <form id="invoice-form" action="/Order/invoice" method="post">
                    <input type="hidden" name="action" value="addReceipt">
                    <input type="hidden" name="totalAmount" id="totalAmountHidden">
                    <table class="selected-invoice">
                        <thead>
                            <tr>
                                <th>Tên</th>
                                <th>Số lượng nhập</th>
                                <th>Tổng tiền</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody id="invoice-table-body">
                            <!-- Các dòng hóa đơn -->
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="2">Tổng tiền biên lai</td>
                                <td colspan="2" id="totalAmount">0 VND</td>
                            </tr>
                        </tfoot>
                    </table>
                    <div class="note-section">
                        <label for="invoice-note">Ghi chú:</label>
                        <textarea id="invoice-note" name="invoiceNote" rows="4" cols="50"></textarea>
                    </div>
                    <button type="button" id="confirm-invoice-btn">Xác nhận nhập hàng</button>
                </form>
            </div>
        </main>
    </div>
</body>
</html>
