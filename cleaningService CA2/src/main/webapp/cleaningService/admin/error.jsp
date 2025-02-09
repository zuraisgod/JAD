<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navigation.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <style>
        .message { margin: 50px auto; text-align: center; font-size: 18px; color: red; }
        .btn { display: block; margin: 20px auto; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>
    <div class="message">
        <h1>${errorMessage}</h1>
    </div>
    <a href="viewBookings" class="btn">Back to Dashboard</a>
</body>
</html>
