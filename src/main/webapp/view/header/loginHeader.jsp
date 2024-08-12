<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="header">
   <img src="${pageContext.request.contextPath}/resource/file/logo.jpg" alt="Logo" class="logo">
   <div class="header-right">
       <span class="header-username">Xin chào, <%= session.getAttribute("username") %></span>
        <a href="/Order/logout" class="header-logout-link">Logout</a>
    </div>
</header>
