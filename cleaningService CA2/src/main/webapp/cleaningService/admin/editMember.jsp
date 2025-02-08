<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.cleaningservice.model.User" %>
<%
    User user = (User) session.getAttribute("user"); // ✅ Use session to fetch the user
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/cleaningService/admin/memberManagement.jsp?error=MemberNotFound");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Member</title>
    <style>
        body { font-family: Arial, sans-serif; }
        form { max-width: 500px; margin: auto; }
        label, input { display: block; width: 100%; margin: 10px 0; }
        input[type="submit"] { background-color: #4CAF50; color: white; border: none; padding: 10px; cursor: pointer; }
        .delete-button { background-color: #f44336; } /* Red delete button */
    </style>
</head>
<body>
    <h2>Edit Member</h2>
    <form action="<%=request.getContextPath()%>/admin/editMember" method="post">
        <input type="hidden" name="userId" value="<%= user.getUserId() %>">
        <label>Name:</label>
        <input type="text" name="name" value="<%= user.getName() %>" required>
        
        <label>Email:</label>
        <input type="email" name="email" value="<%= user.getEmail() %>" required>
        
        <label>Contact Number:</label>
        <input type="text" name="contactNumber" value="<%= user.getContactNumber() %>" required>
        
        <label>Address:</label>
        <input type="text" name="address" value="<%= user.getAddress() %>" required>
        
        <input type="submit" value="Update Member">
    </form>
    
    <form id="deleteForm" action="<%=request.getContextPath()%>/admin/deleteMember" method="post">
        <input type="hidden" name="userId" value="<%= user.getUserId() %>">
        <input type="submit" value="Delete Member" class="delete-button" onclick="return confirmDelete();">
    </form>
    
    <script>
        function confirmDelete() {
            return confirm("Are you sure you want to delete this member?");
        }
    </script>
    
    <br>
    <a href="<%=request.getContextPath()%>/admin/members">Back to Member Management</a>
</body>
</html>
