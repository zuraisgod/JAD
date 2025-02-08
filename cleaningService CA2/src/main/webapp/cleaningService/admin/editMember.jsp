<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cleaningservice.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Member</title>
    <style>
        /* Add your form styling here */
    </style>
</head>
<body>
    <div class="container">
        <h1>Edit Member</h1>

        <% 
            User user = (User) request.getAttribute("user");
            if (user == null) { 
        %>
            <p>Error: Member not found.</p>
        <% } else { %>
        <form action="<%=request.getContextPath()%>/admin/editMember" method="POST">
            <input type="hidden" name="userId" value="<%= user.getUserId() %>" />
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="<%= user.getName() %>" required /><br/>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required /><br/>

            <label for="contactNumber">Contact Number:</label>
            <input type="text" id="contactNumber" name="contactNumber" value="<%= user.getContactNumber() %>" required /><br/>

            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="<%= user.getAddress() %>" required /><br/>

            <button type="submit">Save Changes</button>
        </form>
        <% } %>
    </div>
</body>
</html>
