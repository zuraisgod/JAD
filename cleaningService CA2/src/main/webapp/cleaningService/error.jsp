<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="../css/styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8d7da;
            color: #721c24;
            text-align: center;
            padding: 50px;
        }
        .error-box {
            background: #ffffff;
            border: 1px solid #f5c6cb;
            padding: 20px;
            border-radius: 5px;
            display: inline-block;
        }
        h1 {
            color: #721c24;
        }
        a {
            color: #721c24;
            font-weight: bold;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="error-box">
        <h1>Error Occurred</h1>
        <p>
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) { %>
                <strong><%= errorMessage %></strong>
            <% } else { %>
                <strong>An unexpected error has occurred. Please try again later.</strong>
            <% } %>
        </p>
        <p><a href="login.jsp">Return to Login</a></p>
    </div>
</body>
</html>
