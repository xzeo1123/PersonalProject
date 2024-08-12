<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="sidebar">
    <ul class="sidebar-list">
        <li class="sidebar-item"><a href="/Order/adminorder" data-page="order.jsp"><i class="fas fa-box"></i> Đơn hàng</a></li>
        <li class="sidebar-item"><a href="/Order/category" data-page="category.jsp"><i class="fas fa-grip-lines"></i> Loại sản phẩm</a></li>
        <li class="sidebar-item"><a href="/Order/product" data-page="product.jsp"><i class="fas fa-coffee"></i> Sản phẩm</a></li>
        <li class="sidebar-item"><a href="/Order/invoice" data-page="invoice.jsp"><i class="fas fa-file-invoice"></i> Phiếu nhập</a></li>
        <li class="sidebar-item"><a href="/Order/statistic" data-page="statistic.jsp"><i class="fas fa-chart-line"></i> Thống kê</a></li>
    </ul>
</nav>

<script>
	$(document).ready(function() {
	    var currentPage = localStorage.getItem('activePage');
	    
	    if (currentPage) {
	        $('.sidebar-item a').each(function() {
	            var linkPage = $(this).attr('href').split('/').pop(); 
	            
	            if (linkPage === currentPage) {
	                $(this).parent().addClass('is-active'); 
	            }
	        });
	    }
	
	    // Add click event listener for sidebar items
	    $('.sidebar-item a').on('click', function() {
	        var page = $(this).attr('href').split('/').pop(); 
	        localStorage.setItem('activePage', page);
	
	        $('.sidebar-item').removeClass('is-active');
	        $(this).parent().addClass('is-active');
	    });
	});
</script>
