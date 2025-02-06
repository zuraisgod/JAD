<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .container {
            max-width: 600px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            color: #555;
        }
        input, textarea, button {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }
        textarea {
            resize: vertical;
        }
        button {
            background-color: #28a745;
            color: #fff;
            font-size: 16px;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
        .error-message {
            color: red;
            font-size: 14px;
            text-align: center;
            margin-bottom: 15px;
        }
        .success-message {
            color: green;
            font-size: 14px;
            text-align: center;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<%@ include file="../member/memberNav.jsp" %>
    <div class="container">
        <h1>Profile</h1>
        
        <!-- Success or Error Messages -->
        <c:if test="${param.status == 'updated'}">
            <p class="success-message">Profile updated successfully!</p>
        </c:if>
        <c:if test="${param.status == 'failed'}">
            <p class="error-message">Failed to update profile. Please try again.</p>
        </c:if>
        
        <!-- Profile Form -->
        <form action="${pageContext.request.contextPath}/Profile" method="post">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${user.name}" disabled>
            
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${user.email}" disabled>
            
            <label for="contactNumber">Contact Number:</label>
            <input type="text" id="contactNumber" name="contactNumber" value="${user.contactNumber}" required>
            
            <label for="address">Address:</label>
            <textarea id="address" name="address" rows="3" required>${user.address}</textarea>
            
            <button type="submit">Update Profile</button>
        </form>
    </div>
</body>
</html>
