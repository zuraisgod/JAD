<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navigation.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Success</title>
    <style>
        .message { margin: 50px auto; text-align: center; font-size: 18px; color: green; }
        .btn { display: block; margin: 20px auto; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>
    <div class="message">
        <h1>${successMessage}</h1>
    </div>
    <a href="cleaningService/admin/adminDashboard" class="btn">Back to Dashboard</a>
</body>
</html>
