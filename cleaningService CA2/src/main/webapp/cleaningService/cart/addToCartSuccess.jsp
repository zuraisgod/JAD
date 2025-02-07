<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add to Cart Success</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>
    <div class="container">
        <h1>Cart Status</h1>
        <p><%= request.getAttribute("message") %></p>
        <a href="<%= request.getContextPath() %>/cartServlet">See Your Cart</a>
    </div>
</body>
</html>
